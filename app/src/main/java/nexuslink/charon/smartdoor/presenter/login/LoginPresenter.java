package nexuslink.charon.smartdoor.presenter.login;

import nexuslink.charon.smartdoor.biz.ILoginBiz;
import nexuslink.charon.smartdoor.biz.LoginBiz;
import nexuslink.charon.smartdoor.contract.login.LoginContract;
import nexuslink.charon.smartdoor.listener.OnLoginListener;
import nexuslink.charon.smartdoor.model.login.LoginModel;

import static nexuslink.charon.smartdoor.utils.Constant.ID_LENGTH_ERROR;
import static nexuslink.charon.smartdoor.utils.Constant.PASSWORD_LENGTH_ERROR;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:26
 * 修改人：Charon
 * 修改时间：2017/12/19 19:26
 * 修改备注：
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginModel model;
    private LoginContract.View view;
    private ILoginBiz biz;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.model = new LoginModel();
        biz = new LoginBiz();
    }

    @Override
    public void send(String userId,String password) {
        if (check(userId, password)){
            view.loading(true);
            biz.login(userId, password, new OnLoginListener() {
                @Override
                public void success(LoginModel user) {
                    view.isLog(user);
                    view.showInformation("登录成功");
                    view.loading(false);
                }

                @Override
                public void failed(String s) {
                    view.showInformation(s);
                    view.loading(false);
                }
            });
        }
    }

    @Override
    public boolean check(String userId,String password) {
        int idLength = userId.length();
        int passwordLength = password.length();
        if (idLength != 11) {
            view.showInformation(ID_LENGTH_ERROR);
            return false;
        } else if (passwordLength < 6) {
            view.showInformation(PASSWORD_LENGTH_ERROR);
            return false;
        }
        return true;
    }
}
