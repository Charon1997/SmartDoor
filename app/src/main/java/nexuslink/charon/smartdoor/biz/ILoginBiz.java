package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnLoginListener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 21:15
 * 修改人：Charon
 * 修改时间：2017/12/19 21:15
 * 修改备注：
 */

public interface ILoginBiz {
    void login(String userId, String password, OnLoginListener listener);
}
