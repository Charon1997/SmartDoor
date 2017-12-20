package nexuslink.charon.smartdoor.contract.login;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:27
 * 修改人：Charon
 * 修改时间：2017/12/19 19:27
 * 修改备注：
 */

public interface RegisterContract {
    interface Model {
        boolean checkUserId(String userId);
        boolean checkPasswordEquals(String password,String password2);
        boolean checkCode(String code);
        boolean checkPasswordLength(String password);
        String isLegit(String userId,String code,String password,String password2);
    }

    interface View {
        String getUserId();

        String getCode();

        String getPassword();

        String getPassword2();

        void loading(boolean loading);

        void showInformation(String information);

        void isSend();

        void countDown();

        void codeClickable(boolean clickable);
    }

    interface Presenter {
        void getCode(String userId);

        void verificationCode(String userId, String code,String password,String password2);

        /**
         * 真正的注册
         * @param userId
         * @param password
         */
        void send(String userId,String password);
    }
}
