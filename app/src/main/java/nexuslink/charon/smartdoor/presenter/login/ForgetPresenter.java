package nexuslink.charon.smartdoor.presenter.login;

import android.support.annotation.NonNull;
import android.widget.Toast;

import cn.smssdk.SMSSDK;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import nexuslink.charon.smartdoor.biz.ForgetBiz;
import nexuslink.charon.smartdoor.biz.IForgetBiz;
import nexuslink.charon.smartdoor.contract.login.ForgetContract;
import nexuslink.charon.smartdoor.listener.OnForgetListener;
import nexuslink.charon.smartdoor.model.login.ForgetModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:27
 * 修改人：Charon
 * 修改时间：2017/12/19 19:27
 * 修改备注：
 */

public class ForgetPresenter implements ForgetContract.Presenter {
    private ForgetContract.View view;
    private ForgetModel model;
    private IForgetBiz biz;

    public ForgetPresenter(ForgetContract.View view) {
        this.view = view;
        this.model = new ForgetModel();
        this.biz = new ForgetBiz();
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
        biz.forget(userId, password, new OnForgetListener() {
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
