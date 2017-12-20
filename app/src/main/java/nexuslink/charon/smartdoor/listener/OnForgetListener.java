package nexuslink.charon.smartdoor.listener;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 14:45
 * 修改人：Charon
 * 修改时间：2017/12/20 14:45
 * 修改备注：
 */

public interface OnForgetListener {
    void onSuccess();

    void onFailed(String s);
}
