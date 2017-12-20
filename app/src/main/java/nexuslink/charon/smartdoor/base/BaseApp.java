package nexuslink.charon.smartdoor.base;

import android.app.Application;

import com.mob.MobApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/18 11:56
 * 修改人：Charon
 * 修改时间：2017/12/18 11:56
 * 修改备注：
 */

public class BaseApp extends MobApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //JMessageClient.setDebugMode(true);
        //JMessageClient.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
