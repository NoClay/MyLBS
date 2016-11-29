package andfans.com.mylbs.util;

import android.content.Context;
import android.widget.Toast;

/*
 * Created by 兆鹏 on 2016/11/26.
 */
public class Utils {
    public static void showToast(Context context,CharSequence s){
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
        toast.show();
    }
}
