package android.com.myappfirst.activity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Tara on 24-Mar-17.
 */

public class ToastUtils {

    public static void showToast(Context contxt, String msg, boolean showToastLong){
        int showToast = showToastLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(contxt, msg, showToast).show();
    }
}
