package nexuslink.charon.smartdoor.ui.activity.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nexuslink.charon.smartdoor.ui.activity.main.MainActivity;
import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.base.BaseActivity;
import nexuslink.charon.smartdoor.contract.login.LoginContract;
import nexuslink.charon.smartdoor.model.login.LoginModel;
import nexuslink.charon.smartdoor.presenter.login.LoginPresenter;

import static nexuslink.charon.smartdoor.utils.Constant.KEY_USER;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 21:36
 * 修改人：Charon
 * 修改时间：2017/12/19 21:36
 * 修改备注：
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.logo_login)
    ImageView logoLogin;
    @BindView(R.id.et_id_login)
    EditText etIdLogin;
    @BindView(R.id.et_password_login)
    EditText etPasswordLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget_login)
    TextView tvForgetLogin;
    @BindView(R.id.tv_register_login)
    TextView tvRegisterLogin;

    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    public String getUserId() {
        return etIdLogin.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPasswordLogin.getText().toString();
    }

    @Override
    public void loading(boolean loading) {

    }

    @Override
    public void showInformation(String information) {
        Snackbar.make(logoLogin, information, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void isLog(LoginModel user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(KEY_USER, user);
        startActivity(intent);
        finish();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_login, R.id.tv_forget_login, R.id.tv_register_login})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.send(getUserId(), getPassword());
                break;
            case R.id.tv_forget_login:
                intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register_login:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
