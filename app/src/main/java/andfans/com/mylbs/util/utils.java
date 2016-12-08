package andfans.com.mylbs.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class Utils {
    public static boolean isRegister = false;
    public static boolean isLoading = false;
    //缓存头像使用的目录
    public static final String PATH_ADD = Environment.getExternalStorageDirectory() +
            "/MyLBS/userImage/";
    public static void showToast(Context context,CharSequence s){
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
        toast.show();
    }
}
