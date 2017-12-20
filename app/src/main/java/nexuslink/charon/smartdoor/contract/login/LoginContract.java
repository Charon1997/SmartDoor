package nexuslink.charon.smartdoor.contract.login;

import nexuslink.charon.smartdoor.model.login.LoginModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:26
 * 修改人：Charon
 * 修改时间：2017/12/19 19:26
 * 修改备注：
 */

public interface LoginContract {
    interface Model {
    }

    interface View {
        String getUserId();

        String getPassword();

        void loading(boolean loading);

        void showInformation(String information);

        void isLog(LoginModel user);
    }

    interface Presenter {
        void send(String userId,String password);
        boolean check(String userId,String password);
    }
}
