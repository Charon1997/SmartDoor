package nexuslink.charon.smartdoor.listener;

import nexuslink.charon.smartdoor.model.login.LoginModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 21:17
 * 修改人：Charon
 * 修改时间：2017/12/19 21:17
 * 修改备注：
 */

public interface OnLoginListener {
    void success(LoginModel user);

    void failed(String s);
}
