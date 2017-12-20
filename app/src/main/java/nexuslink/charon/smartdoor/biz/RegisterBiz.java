package nexuslink.charon.smartdoor.biz;

import nexuslink.charon.smartdoor.listener.OnRegisterListener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 16:32
 * 修改人：Charon
 * 修改时间：2017/12/20 16:32
 * 修改备注：
 */

public class RegisterBiz implements IRegisterBiz {
    @Override
    public void register(String userId, String password, OnRegisterListener listener) {
        //// TODO: 2017/12/20 调用接口
        listener.onSuccess();
    }
}
