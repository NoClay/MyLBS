package andfans.com.mylbs.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import andfans.com.mylbs.R;
import andfans.com.mylbs.util.MyImageButton;
import andfans.com.mylbs.util.RecycleAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 * Created by 兆鹏 on 2016/11/23.
 */
public class Fragment_Shouye extends Fragment {
    private Context context;
    private RecyclerView recyclerView01;
    private RecycleAdapter adapter01;
    private List<Map<String,Object>> datas01;
    private MyImageButton bt1,bt2,bt3,bt4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment01_layout,container,false);
        context = getContext();
        findView(v);
        return v;
    }

    private void findView(View v) {
        recyclerView01 = (RecyclerView) v.findViewById(R.id.id_shouye_rv01);
        bt1 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt01);
        bt2 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt02);
        bt3 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt03);
        bt4 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt04);
        bt1.setText("出租车位");
        bt1.setTextColors(R.color.black);
        bt1.setTextSize(15);
        bt2.setText("附近美食");
        bt2.setTextColors(R.color.black);
        bt2.setTextSize(15);
        bt3.setText("附近酒店");
        bt3.setTextColors(R.color.black);
        bt3.setTextSize(15);
        bt4.setText("附近加油站");
        bt4.setTextColors(R.color.black);
        bt4.setTextSize(15);
        bt1.setPicture(R.drawable.rent);
        bt2.setPicture(R.drawable.goodfood);
        bt3.setPicture(R.drawable.jiudian);
        bt4.setPicture(R.drawable.addgas);
        GridLayoutManager layoutManager01 = new GridLayoutManager(context,1);
        recyclerView01.setLayoutManager(layoutManager01);
        recyclerView01.setHasFixedSize(true);
        getData01();
    }

    private void getData01() {
        Observable.create((Observable.OnSubscribe<List<Map<String, Object>>>) subscriber -> {
            try {
                datas01 = new ArrayList<>();
                String[] text = {"出租车位","附近美食","附近酒店","附近加油站"};
                int [] id = {
                        R.drawable.rent,R.drawable.goodfood,R.drawable.jiudian,R.drawable.addgas
                };
                for(int i = 0;i < text.length;i++){
                    Map<String,Object> data = new HashMap<>();
                    data.put("text",text[i]);
                    data.put("id",id[i]);
                    datas01.add(data);
                }
                subscriber.onNext(datas01);
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Map<String, Object>> maps) {
                        adapter01 = new RecycleAdapter(maps,R.layout.recycle_item01_layout);
                        recyclerView01.setAdapter(adapter01);
                        adapter01.setOnItemClickListener((view,position) -> {
                            switch (position){
                                case 0:
                                    Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                    break;
                                default:break;
                            }
                        });
                    }
                });
    }
}
