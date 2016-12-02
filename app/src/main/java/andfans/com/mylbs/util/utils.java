package andfans.com.mylbs.util;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static boolean isRegister = false;
    public static boolean isLoading = false;
    public static void showToast(Context context,CharSequence s){
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
        toast.show();
    }
}
