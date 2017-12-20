package nexuslink.charon.smartdoor.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import kr.co.namee.permissiongen.PermissionGen;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/18 11:55
 * 修改人：Charon
 * 修改时间：2017/12/18 11:55
 * 修改备注：
 */

public abstract class BaseActivity extends AppCompatActivity {
    private View mContextView = null;
    private static long msgId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = bindView();
        if (view == null) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else {
            mContextView = view;
        }
        setContentView(mContextView);
        initData();
        initView();
        doSomething();
    }




    @Override
    protected void onDestroy() {
        //JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    protected abstract int bindLayout();

    protected abstract View bindView();

    protected abstract void doSomething();

    protected abstract void initView();

    protected abstract void initData();


}
