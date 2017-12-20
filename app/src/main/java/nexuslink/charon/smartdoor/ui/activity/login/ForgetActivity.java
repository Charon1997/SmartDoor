package nexuslink.charon.smartdoor.ui.activity.login;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.base.BaseActivity;
import nexuslink.charon.smartdoor.contract.login.ForgetContract;
import nexuslink.charon.smartdoor.presenter.login.ForgetPresenter;
import nexuslink.charon.smartdoor.utils.SystemUtils;

import static nexuslink.charon.smartdoor.utils.Constant.COUNT_DOWN_SECOND;
import static nexuslink.charon.smartdoor.utils.Constant.CURRENT_TIME;
import static nexuslink.charon.smartdoor.utils.Constant.FORGET_COUNT_DOWN_TIME;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 13:48
 * 修改人：Charon
 * 修改时间：2017/12/20 13:48
 * 修改备注：
 */

public class ForgetActivity extends BaseActivity implements ForgetContract.View {
    @BindView(R.id.tv_back_forget)
    TextView tvBackForget;
    @BindView(R.id.et_id_forget)
    EditText etIdForget;
    @BindView(R.id.et_code_forget)
    EditText etCodeForget;
    @BindView(R.id.btn_code_forget)
    Button btnCodeForget;
    @BindView(R.id.et_password_forget)
    EditText etPasswordForget;
    @BindView(R.id.et_password2_forget)
    EditText etPassword2Forget;
    @BindView(R.id.btn_forget)
    Button btnForget;

    private SharedPreferences sp;
    private ForgetPresenter presenter;
    private EventHandler eventHandler;
    private CountDownTimer timer;

    @Override
    protected int bindLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        initSms();
        presenter = new ForgetPresenter(this);
    }

    private void initPermission() {
        PermissionGen.with(ForgetActivity.this).addRequestCode(100)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE).request();
    }

    private void initSms() {
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (o instanceof Throwable) {
                    Throwable throwable = (Throwable) o;
                    final String msg = throwable.getMessage();

                    showInformation(msg.substring(24, msg.length() - 2));

                } else {
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Log.d("TAG", "验证成功");
                            presenter.send(getUserId(), getPassword());
                        } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            showInformation("验证码已发送");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countDown();
                                }
                            });
                        } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                            //语音
                        } else if (i == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initPermission();
        sp = getSharedPreferences(FORGET_COUNT_DOWN_TIME, Context.MODE_PRIVATE);
    }

    @Override
    public String getUserId() {
        return etIdForget.getText().toString();
    }

    @Override
    public String getCode() {
        return etCodeForget.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPasswordForget.getText().toString();
    }

    @Override
    public String getPassword2() {
        return etPassword2Forget.getText().toString();
    }

    @Override
    public void loading(boolean loading) {

    }

    @Override
    public void showInformation(final String information) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetActivity.this, information, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void isSend() {
        finish();
    }

    @Override
    public void countDown() {
        codeClickable(false);
        timer = new CountDownTimer(COUNT_DOWN_SECOND * 1000, 1000) {
            @Override
            public void onTick(long l) {
                btnCodeForget.setText(l / 1000 + "秒后可重发");
            }

            @Override
            public void onFinish() {
                codeClickable(true);
                btnCodeForget.setText("获取验证码");
            }
        };

        timer.start();
    }

    @Override
    public void codeClickable(boolean clickable) {
        Button button = btnCodeForget;
        button.setClickable(clickable);
        if (clickable) {
            button.setBackgroundResource(R.drawable.btn_down);
        } else {
            button.setBackgroundResource(R.drawable.btn_unclick);
        }
    }

    @OnClick({R.id.tv_back_forget, R.id.btn_code_forget, R.id.btn_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_forget:
                finish();
                break;
            case R.id.btn_code_forget:
                SystemUtils.hideSoftKeyboard((InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE), getWindow());
                presenter.getCode(getUserId());
                break;
            case R.id.btn_forget:
                presenter.verificationCode(getUserId(), getCode(), getPassword(), getPassword2());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSuccess() {
        showInformation("已经获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void doFail() {
        showInformation("获取权限失败");
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCountDown();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(FORGET_COUNT_DOWN_TIME, SystemUtils.readTime(btnCodeForget));
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        editor.apply();
    }

    public void isCountDown() {
        long remainingTime = sp.getLong(FORGET_COUNT_DOWN_TIME, 0);
        final Button button = btnCodeForget;
        long restTime = (remainingTime - ((System.currentTimeMillis() - sp.getLong(CURRENT_TIME, 0)) / 1000));
        if (restTime > 0) {
            timer = new CountDownTimer(restTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    codeClickable(false);
                    button.setText(millisUntilFinished / 1000 + "秒后可重发");
                }

                @Override
                public void onFinish() {
                    codeClickable(true);
                    button.setText("获取验证码");
                }
            };
            timer.start();
        }
    }
}
