package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnRegisterListener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 16:24
 * 修改人：Charon
 * 修改时间：2017/12/20 16:24
 * 修改备注：
 */

public interface IRegisterBiz {
    void register(String userId, String password, OnRegisterListener listener);
}
