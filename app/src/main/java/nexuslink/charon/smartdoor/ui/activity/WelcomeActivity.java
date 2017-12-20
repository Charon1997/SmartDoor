package nexuslink.charon.smartdoor.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import nexuslink.charon.smartdoor.ui.activity.main.MainActivity;
import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.base.BaseActivity;
import nexuslink.charon.smartdoor.model.login.LoginModel;
import nexuslink.charon.smartdoor.ui.activity.login.LoginActivity;

import static nexuslink.charon.smartdoor.utils.Constant.KEY_USER;
import static nexuslink.charon.smartdoor.utils.Constant.PASSWORD;
import static nexuslink.charon.smartdoor.utils.Constant.USERNAME;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 15:46
 * 修改人：Charon
 * 修改时间：2017/12/19 15:46
 * 修改备注：
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.welcome_ll)
    RelativeLayout welcomeLl;
    private SharedPreferences sp;


    private int duration = 1500;


    @Override
    protected int bindLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        showAnimator();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是否首次登陆
                checkUser(false);
            }
        }, (duration +500));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    private void checkUser(boolean have) {
        if (have) {
            String username = sp.getString(USERNAME, "");
            String password = sp.getString(PASSWORD, "");
            toIntent(MainActivity.class, new LoginModel(username, password));
        } else {
            toIntent(LoginActivity.class, null);
        }
    }

    private void toIntent(Class mainActivityClass, LoginModel user) {
        Intent intent = new Intent(WelcomeActivity.this, mainActivityClass);
        if (user != null) {
            intent.putExtra(KEY_USER, user);
        }
        startActivity(intent);
        finish();
    }

    private void showAnimator() {
        ObjectAnimator ob = ObjectAnimator.ofFloat(welcomeLl, "Alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.play(ob);
        set.setDuration(duration);
        set.start();
    }
}
