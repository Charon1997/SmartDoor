package nexuslink.charon.smartdoor.ui.pop;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.ui.adapter.PopViewPagerAdapter;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 21:52
 * 修改人：Charon
 * 修改时间：2017/12/20 21:52
 * 修改备注：
 */
// TODO: 2017/12/20 重构框架

public class MPopWindow extends PopupWindow {
    private static final String TAG = MPopWindow.class.getSimpleName();
    public Context mContext;
    private List<View> list;
    private List<ItemBean> modeList = new ArrayList<>();
    private List<ItemBean> doorList = new ArrayList<>();
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private int imgDoor[] = {R.drawable.door1};
    private int background[] = {R.drawable.background2};
    private TabLayout mTL;
    private NoScrollViewPager mVp;
    private IMainView mainView;
    //private Button button;

    public MPopWindow(Context context, IMainView mainView) {
        this.mainView = mainView;
        initView(context);
    }

    private void addDoor() {
        for (int i = 0; i < imgDoor.length; i++) {
            ItemBean itemBean = new ItemBean(imgDoor[i], "门" + i);
            doorList.add(itemBean);
        }
        for (int i = 0; i < background.length; i++) {
            ItemBean itemBean = new ItemBean(background[i], "风格" + i);
            modeList.add(itemBean);
        }
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_pop,
                null);
        setContentView(v);

        mTL = (TabLayout) v.findViewById(R.id.pop_tab);
        mVp = (NoScrollViewPager) v.findViewById(R.id.pop_viewpager);
        addDoor();
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点�?
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        // 刷新状�?
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwindow_from_bottom);
        addView();
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0xffffff);
        // 设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);
        PopViewPagerAdapter viewPagerAdapter = new PopViewPagerAdapter(list);
        mVp.setAdapter(viewPagerAdapter);
        mTL.setupWithViewPager(mVp);
    }

    private void addView() {
        list = new ArrayList<>();
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.pop_list, null);
        recyclerView1 = (RecyclerView) view1.findViewById(R.id.pop_recycler);
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(manager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        RecDoorAdapter adapter1 = new RecDoorAdapter(mContext, doorList, mainView);
        recyclerView1.setAdapter(adapter1);
        list.add(view1);


        View view2 = LayoutInflater.from(mContext).inflate(R.layout.pop_list, null);
        recyclerView2 = (RecyclerView) view2.findViewById(R.id.pop_recycler);
        list.add(view2);

        View view3 = LayoutInflater.from(mContext).inflate(R.layout.pop_list, null);
        recyclerView3 = (RecyclerView) view3.findViewById(R.id.pop_recycler);
        LinearLayoutManager manager3 = new LinearLayoutManager(mContext);
        manager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(manager3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        RecModeAdapter adapter3 = new RecModeAdapter(mContext, modeList, mainView);
        recyclerView3.setAdapter(adapter3);
        list.add(view3);
    }

    public void showPopupWindow(View parent) {
        //float height = ScreenUtils.dip2px(mContext, 148);
        if (!this.isShowing()) {
            //动画
            Log.d(TAG, "showPopupWindow: show");
//            AnimatorSet set = new AnimatorSet();
//            ObjectAnimator ob1 = ObjectAnimator.ofFloat(button, "TranslationY", 0, -height);
//            ObjectAnimator ob2 = ObjectAnimator.ofFloat(button, "rotation", 0, 180);
//            set.playTogether(ob1, ob2);
//            set.setDuration(300);
//            set.start();
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            Log.d(TAG, "showPopupWindow: close");
//            AnimatorSet set = new AnimatorSet();
//            ObjectAnimator ob1 = ObjectAnimator.ofFloat(button, "TranslationY", -height, 0);
//            ObjectAnimator ob2 = ObjectAnimator.ofFloat(button, "rotation", 180, 360);
//            set.playTogether(ob1, ob2);
//            set.setDuration(300);
//            set.start();
            this.dismiss();
        }
    }


    public interface onGetTypeClickListener {
        void getType(int type);

        void getImgUri(Uri imgUri, File file);
    }

    private onGetTypeClickListener listener;

    public void setOnGetTypeClickListener(onGetTypeClickListener listener) {
        this.listener = listener;
    }

}
