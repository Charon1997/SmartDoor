package nexuslink.charon.smartdoor.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2018/3/6 19:31
 * 修改人：Charon
 * 修改时间：2018/3/6 19:31
 * 修改备注：
 */

public class DragImageView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = DragImageView.class.getSimpleName();
    private int mode = 0;
    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;

    private PointF startPoint = new PointF();
    private Matrix matrix = new Matrix();
    private Matrix currentMatrix = new Matrix();

    private float startDis;
    private PointF midPoint;

    public boolean isFirst;
    private float doubleScale;
    private float defaultScale;
    private float mixScale;
    private float allScale = 1.0f;
    private float curScale = 1.0f;

    private float maxDx, maxDy, allDx, allDy, curDx, curDy;
    private Matrix mScaleMatrix = new Matrix();


    public DragImageView(Context context) {
        this(context, null);
        setOnTouchListener(this);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        setOnTouchListener(this);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float scale = 1.0f;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                currentMatrix.set(getImageMatrix());
                startPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG) {
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    curDx = dx;
                    curDy = dy;
                    Log.d(TAG, "onTouch: curDx" + curDx + "curDy" + curDy + "maxDx" + maxDx + "maxDy" + maxDy);

                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                } else if (mode == MODE_ZOOM) {
                    float endDis = distance(event);
                    if (endDis > 10f) {
                        scale = endDis / startDis;
                        curScale = scale;
                        //Log.d(TAG, "onTouch: scale"+scale);
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                break;
            //触电离开屏幕，但屏幕上还有其他点
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onTouch: ACTION_POINTER_UP");
                allScale *= curScale;
                //Log.d(TAG, "onTouch: allScale" + allScale +"cur"+curScale);
                //最小缩放距离
                if (allScale < 0.5f) {
                    scale = 1.0f;
                    allScale /= curScale;
                    matrix.set(currentMatrix);
                    matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                }


            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouch: ACTION_UP");
                if (mode == MODE_DRAG && allScale < 1.25f) {
                    float dx = curDx, dy = curDy;
                    float lastDx = allDx, lastDy = allDy;
                    allDx += curDx;
                    allDy += curDy;
                    Log.d(TAG, "onTouch: alldx" + allDx + "alldy" + allDy);
                    if (Math.abs(allDx) > maxDx) {
                        if (curDx < 0) {
                            allDx = -maxDx;
                        } else {
                            allDx = maxDx;
                        }
                        dx = allDx - lastDx;
                    }
                    if (Math.abs(allDy) > maxDy) {
                        if (curDy < 0) {
                            allDy = -maxDy;
                        } else {
                            allDy = maxDy;
                        }
                        dy = allDy - lastDy;
                    }

                    Log.d(TAG, "onTouchafter: alldx" + allDx + "alldy" + allDy);
                    Log.d(TAG, "onTouchafter: dx" + dx + "dy" + dy);
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                    mode = 0;
                }
                break;
            //多个点压下
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                startDis = distance(event);
                if (startDis > 10f) {
                    midPoint = mid(event);
                    currentMatrix.set(getImageMatrix());
                }
                break;
            default:
                break;
        }
        setImageMatrix(matrix);
        return true;
    }


    private float distance(MotionEvent event) {
        //第一点和第二点的x距离
        float dx = 0, dy = 0;
        try {
            dx = event.getX(1) - event.getX(0);
            dy = event.getY(1) - event.getY(0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (!isFirst) {
            isFirst = true;
            int width = getWidth();

            int height = getHeight();
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int imageWidth = drawable.getIntrinsicWidth();
            int imageHeight = drawable.getIntrinsicHeight();
            float scale = 1.0f;
            if (imageWidth > width && imageHeight < height) {
                scale = width * 1.0f / imageWidth;
            }
            if (imageWidth < width && imageHeight > height) {
                scale = height * 1.0f / imageHeight;
            }
            if ((imageWidth > width && imageHeight > height) || (imageWidth < width && imageHeight < height)) {
                scale = Math.min(width * 1.0f / imageWidth, height * 1.0f / imageHeight);
            }
            defaultScale = (float) (scale * 0.8);
            Log.d("TAG", "onGlobalLayout: scale" + scale);
            doubleScale = defaultScale * 2;
            mixScale = defaultScale / 3;
            int dx = width / 2 - imageWidth / 2;
            int dy = height / 2 - imageHeight / 2;
            maxDx = (width - imageWidth * defaultScale) / 2;
            maxDy = (height - imageHeight * defaultScale) / 2;
            matrix.postTranslate(dx, dy);
            matrix.postScale(defaultScale, defaultScale, width / 2, height / 2);
            setImageMatrix(matrix);
        }
    }
}
