package nexuslink.charon.smartdoor.utils;

import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/20 16:47
 * 修改人：Charon
 * 修改时间：2017/12/20 16:47
 * 修改备注：
 */

public class SystemUtils {
    public static void hideSoftKeyboard(InputMethodManager manager, Window window) {
        if (manager != null) {
            manager.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
        }
    }

    public static long readTime(Button button) {
        long time = 0;
        try {
            time = Long.parseLong(button.getText().toString().substring(0, button.getText().length() - 5));
        } catch (NumberFormatException e) {
            Log.e("TAG", "readTime: " + e);
        }
        return time;
    }
}
