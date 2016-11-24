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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
public class Fragment_Shouye extends Fragment implements View.OnClickListener{
    private static Context context;
    private RecyclerView recyclerView01;
    private RecycleAdapter adapter01 = null;
    private List<Map<String,Object>> datas01;
    private String [] datas = {"西安","北京","上海","广州"};
    private ArrayAdapter<String> adapter;//spinner的适配器
    private MyImageButton bt1,bt2,bt3,bt4;
    public static final int MSG = 1;
    private Spinner sp;
    private EditText ed;
    private ImageView im;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment01_layout,container,false);
        context = getContext();
        datas01 = new ArrayList<>();
        findView(v);
        return v;
    }

    private void findView(View v) {
        sp = (Spinner) v.findViewById(R.id.id_shouye_sp);
        im = (ImageView) v.findViewById(R.id.id_shouye_im);
        ed = (EditText) v.findViewById(R.id.id_shouye_ed);
        recyclerView01 = (RecyclerView) v.findViewById(R.id.id_shouye_rv01);
        bt1 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt01);
        bt2 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt02);
        bt3 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt03);
        bt4 = (MyImageButton) v.findViewById(R.id.id_shouye_imbt04);
        im.setOnClickListener(this);
        ed.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,"点击了"+datas[i],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                String[] text = {"帝都广场停车场"+"\n"+"收费标准:20￥/小时"+"\n"+"闲时时间:2:00-6:00","万达广场停车场"+"\n"+"收费标准:20￥/小时"+"\n"+"闲时时间:16:00-20:00","华润广场停车场"+"\n"+"收费标准:20￥/小时"+"\n"+"闲时时间:13:00-15:00","中南海广场停车场"+"\n"+"收费标准:20￥/小时"+"\n"+"闲时时间:8:00-12:00"};
                String [] id = {
                       "http://pic.anhuinews.com/0/03/16/30/3163049_744230.jpg",
                        "http://zc.114chn.com/tradepic/440183/4401831005310001/fckupload/201105261528065781.jpg",
                        "http://ht.topbiz360.com/web/upimg/upload/20140606/14020237686964.jpeg",
                        "http://www.weblz.com.cn/upload/images/2016/4/19172416935.jpg"
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
                        adapter01 = new RecycleAdapter(context,maps,R.layout.recycle_item01_layout);
                        recyclerView01.setAdapter(adapter01);
                        //adapter01.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //搜索输入框
            case R.id.id_shouye_ed:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;
            //消息提示
            case R.id.id_shouye_im:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.id_shouye_imbt01:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.id_shouye_imbt02:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.id_shouye_imbt03:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.id_shouye_imbt04:
                Snackbar.make(view,"点击了"+view.getClass(),Snackbar.LENGTH_SHORT).show();
                break;

        }
    }
}

