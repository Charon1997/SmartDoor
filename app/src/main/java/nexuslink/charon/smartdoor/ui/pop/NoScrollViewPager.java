package nexuslink.charon.smartdoor.ui.pop;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 21:54
 * 修改人：Charon
 * 修改时间：2017/12/20 21:54
 * 修改备注：
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
