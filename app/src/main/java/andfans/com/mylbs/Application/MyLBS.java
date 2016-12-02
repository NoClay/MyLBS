package andfans.com.mylbs.Application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

/*
 * Created by 兆鹏 on 2016/11/25.
 */
public class MyLBS extends Application {
    private BroadcastReceiver receiver;
    @Override
    public void onCreate() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        super.onCreate();
        Bmob.initialize(this,"58d3c7005c6ca0f14436244e2bf0f75c");
        BmobSMS.initialize(this,"58d3c7005c6ca0f14436244e2bf0f75c");
        SDKInitializer.initialize(this);
        //registerSDKCheckReceiver();
    }

    private void registerSDKCheckReceiver(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action= intent.getAction();
                if(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
                }else if(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"验证错误",Toast.LENGTH_SHORT).show();
                }

            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(receiver,filter);
    }


}
