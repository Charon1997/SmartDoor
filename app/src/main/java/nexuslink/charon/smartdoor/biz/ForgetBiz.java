package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnForgetListener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 14:47
 * 修改人：Charon
 * 修改时间：2017/12/20 14:47
 * 修改备注：
 */

public class ForgetBiz implements IForgetBiz {
    @Override
    public void forget(String userId, String password, OnForgetListener listener) {
        // TODO: 2017/12/20 网络请求
        listener.onSuccess();
    }
}
