package nexuslink.charon.smartdoor.presenter.login;

import cn.smssdk.SMSSDK;
import nexuslink.charon.smartdoor.biz.IRegisterBiz;
import nexuslink.charon.smartdoor.biz.RegisterBiz;
import nexuslink.charon.smartdoor.contract.login.RegisterContract;
import nexuslink.charon.smartdoor.listener.OnRegisterListener;
import nexuslink.charon.smartdoor.model.login.RegisterModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:27
 * 修改人：Charon
 * 修改时间：2017/12/19 19:27
 * 修改备注：
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private RegisterModel model;
    private IRegisterBiz biz;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.model = new RegisterModel();
        this.biz = new RegisterBiz();
    }

    @Override
    public void getCode(String userId) {
        if (model.checkUserId(userId)) {
            SMSSDK.getVerificationCode("86",userId);
        } else {
            view.showInformation("号码输入错误");
        }
    }

    @Override
    public void verificationCode(String userId, String code, String password, String password2) {
        String legit = model.isLegit(userId, code, password, password2);
        if ("正确".equals(legit)) {
            SMSSDK.submitVerificationCode("86", userId, code);
        } else {
            view.showInformation(legit);
        }
    }

    @Override
    public void send(String userId, String password) {
        view.loading(true);
        biz.register(userId, password, new OnRegisterListener() {
            @Override
            public void onSuccess() {
                view.isSend();
                view.showInformation("修改成功");
                view.loading(false);
            }

            @Override
            public void onFailed(String s) {
                view.showInformation(s);
                view.loading(false);
            }
        });
    }
}
