package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnLoginListener;
import nexuslink.charon.smartdoor.model.login.LoginModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 21:27
 * 修改人：Charon
 * 修改时间：2017/12/19 21:27
 * 修改备注：
 */

public class LoginBiz implements ILoginBiz {
    @Override
    public void login(String userId, String password, OnLoginListener listener) {
        // TODO: 2017/12/20 网络请求 
        listener.success(new LoginModel(userId,password));
    }
}
