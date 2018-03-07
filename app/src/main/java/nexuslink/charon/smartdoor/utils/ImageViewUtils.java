package nexuslink.charon.smartdoor.utils;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nexuslink.charon.smartdoor.ui.DragImageView;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2018/3/6 16:22
 * 修改人：Charon
 * 修改时间：2018/3/6 16:22
 * 修改备注：
 */

public class ImageViewUtils {
    private static List<DragImageView> imageList = new ArrayList<>();
    private static int curId = 0;
    private static long time = 0;

    public static void createImage(Context context) {
        if (imageList.size() == 2) {
            DragImageView img1 = new DragImageView(context);
            img1.setScaleType(ImageView.ScaleType.MATRIX);
            imageList.add(img1);
        } else {
            DragImageView img1 = new DragImageView(context);
            img1.setScaleType(ImageView.ScaleType.MATRIX);
            imageList.add(img1);
            DragImageView img2 = new DragImageView(context);
            img2.setScaleType(ImageView.ScaleType.MATRIX);
            imageList.add(img2);
        }
    }

    public synchronized static DragImageView getImageView(Context context, int width, int height, int resource, ViewGroup parent) {
        createImage(context);
        DragImageView imageView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        imageView = imageList.get(curId);

        imageView.setLayoutParams(params);
        imageView.setImageResource(resource);

//        for (int i = 0;i < parent.getChildCount();i++ ) {
//            if (parent.getChildAt(i) == imageView) {
//                parent.removeView(imageView);
//                break;
//            }
//        }
        parent.addView(imageView);
        return imageView;
    }

    public synchronized static void removeImg(DragImageView img, ViewGroup parent) {
        if (curId != 0) {
            parent.removeView(imageList.get(0));
            imageList.remove(0);
        }
        curId = 1;
    }


    public static boolean canMove(long currentTime) {
        if (currentTime - time > 1000) {
            time = currentTime;
            return true;
        } else {
            return false;
        }
    }
}
