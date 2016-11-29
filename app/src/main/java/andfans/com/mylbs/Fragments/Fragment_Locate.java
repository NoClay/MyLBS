package andfans.com.mylbs.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import andfans.com.mylbs.R;

/*
 * Created by 兆鹏 on 2016/11/23.
 */
public class Fragment_Locate extends Fragment {

    private static final LatLng xiyou = new LatLng(34.158747363727284,108.91084848346705);
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment02_layout,container,false);
        mapView = (MapView) v.findViewById(R.id.id_Locate_map);
        BaiduMap baiduMap = mapView.getMap();//得到地图的控制器
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(xiyou));//设置地图的中心点
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));//设置地图的缩放程度
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
