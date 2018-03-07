package nexuslink.charon.smartdoor.ui.activity.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.base.BaseActivity;
import nexuslink.charon.smartdoor.contract.main.MainContract;
import nexuslink.charon.smartdoor.ui.DragImageView;
import nexuslink.charon.smartdoor.ui.pop.MPopWindow;
import nexuslink.charon.smartdoor.utils.ImageViewUtils;
import nexuslink.charon.smartdoor.utils.ScreenUtils;

import static nexuslink.charon.smartdoor.utils.Constant.BACK_IMG_LIST;
import static nexuslink.charon.smartdoor.utils.Constant.DURATION;

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.main_back)
    RelativeLayout mainBack;
    @BindView(R.id.main_tip_img)
    ImageView mainTipImg;

    private static boolean TIP_OPEN = false;

    private MPopWindow mPopWindow;

    private MainContract.View mainView = this;
    private  DragImageView dragImg;

    /**
     * false代表1，true代表2
     */
    private static boolean currentImg = false;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        mPopWindow = new MPopWindow(this, mainView);
        mPopWindow.setFocusable(false);
        mPopWindow.setOutsideTouchable(false);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.main_tip_img)
    public void onViewClicked() {
        Log.d("TAG", "onViewClicked tip ");
        translationTip();
    }

    @Override
    public void translationDoor(int startX, int startY, int width, int height, int resource) {
        if (ImageViewUtils.canMove(System.currentTimeMillis())) {
            dragImg = ImageViewUtils.getImageView(this,width, height, resource, mainBack);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ImageViewUtils.removeImg(dragImg,mainBack);
                }
            },DURATION);
            animatorImg(dragImg,startX,startY);
        }
    }

    public void animatorImg(ImageView view,int startX,int startY){

            AnimatorSet set = new AnimatorSet();
            ObjectAnimator ob1 = ObjectAnimator.ofFloat(view,"translationX",startX,1400);
            ObjectAnimator ob2 = ObjectAnimator.ofFloat(view,"translationY",startY,300);
            ObjectAnimator ob3 = ObjectAnimator.ofFloat(view,"ScaleX",1.0f,0.0f);
            ObjectAnimator ob4 = ObjectAnimator.ofFloat(view,"ScaleY",1.0f,0.0f);
            ObjectAnimator ob5 = ObjectAnimator.ofFloat(view, "ScaleX", 0.0f, 2.9f);
            ObjectAnimator ob6 = ObjectAnimator.ofFloat(view, "ScaleY", 0.0f, 4.1f);
            set.playTogether(ob1,ob2,ob3,ob4);
            set.play(ob5).after(ob4).with(ob6);
            set.setDuration(DURATION/2);
            set.setInterpolator(new AccelerateInterpolator());
            set.start();

    }

    @Override
    public void translationTip() {
        float height = ScreenUtils.dip2px(this, 148);
        Log.d("MainTipClick", "translationTip: height" + height);
        if (TIP_OPEN) {
            //打开--->关闭
            TIP_OPEN = false;
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator ob1 = ObjectAnimator.ofFloat(mainTipImg, "TranslationY", -height, 0);
            ObjectAnimator ob2 = ObjectAnimator.ofFloat(mainTipImg, "rotation", 180, 360);
            set.playTogether(ob1, ob2);
            set.setDuration(300);
            set.start();
            mPopWindow.showPopupWindow(LayoutInflater.from(this).inflate(R.layout.activity_main, null));
        } else {
            //关闭--->打开
            TIP_OPEN = true;
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator ob1 = ObjectAnimator.ofFloat(mainTipImg, "TranslationY", 0, -height);
            ObjectAnimator ob2 = ObjectAnimator.ofFloat(mainTipImg, "rotation", 0, 180);
            set.playTogether(ob1, ob2);
            set.setDuration(300);
            set.start();
            mPopWindow.showPopupWindow(LayoutInflater.from(this).inflate(R.layout.activity_main, null));

        }
    }

    @Override
    public void changeMode(int id) {
        mainBack.setBackgroundResource(BACK_IMG_LIST[id]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0;i < mainBack.getChildCount();i++) {
            if (mainBack.getChildAt(i) instanceof DragImageView) {
                mainBack.removeView(mainBack.getChildAt(i));
            }
        }
        translationTip();
    }
}
