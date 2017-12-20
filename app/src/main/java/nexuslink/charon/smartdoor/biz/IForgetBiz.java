package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnForgetListener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 14:44
 * 修改人：Charon
 * 修改时间：2017/12/20 14:44
 * 修改备注：
 */

public interface IForgetBiz {
    void forget(String userId,String password,OnForgetListener listener);
}
