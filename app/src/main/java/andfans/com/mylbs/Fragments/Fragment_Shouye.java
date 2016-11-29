package andfans.com.mylbs.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CommonRecyclerViewAdapter.Adapter.CommonAdapter;
import CommonRecyclerViewAdapter.Adapter.MultiItemTypeAdapter;
import CommonRecyclerViewAdapter.base.ViewHolder;
import CommonRecyclerViewAdapter.wrapper.EmptyWrapper;
import CommonRecyclerViewAdapter.wrapper.HeaderAndFooterWrapper;
import CommonRecyclerViewAdapter.wrapper.LoadMoreWrapper;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.MyImageButton;
import andfans.com.mylbs.util.MyRxViewPager;
import andfans.com.mylbs.util.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 * Created by 兆鹏 on 2016/11/29.
 */
public class Fragment_Shouye extends Fragment {
    private RecyclerView recyclerView;
    private List<Map<String,Object>> datas;
    private CommonAdapter<Map<String,Object>> adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private Context context;
    private String [] datasSpinner = {"西安","北京","上海","广州"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment01_layout,container,false);
        context = getContext();
        findView(v);
        return v;
    }

    private void findView(View v) {
        datas = new ArrayList<>();
        Spinner sp = (Spinner) v.findViewById(R.id.id_shouye_sp);
        ImageView im = (ImageView) v.findViewById(R.id.id_shouye_im);
        EditText ed = (EditText) v.findViewById(R.id.id_shouye_ed);
        recyclerView = (RecyclerView) v.findViewById(R.id.id_shouye_rv01);
        im.setOnClickListener(view -> Utils.showToast(context,"当前没有新消息"));
        ed.setOnClickListener(view -> Utils.showToast(context,"服务器忙"));
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, datasSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapterSpinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(context,"点击了"+datas[i],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        Observable.create((Observable.OnSubscribe<List<Map<String, Object>>>) subscriber -> {
            try {
                datas = initDatas();
                if(datas.size() != 0) {
                    subscriber.onNext(datas);
                }else {
                    subscriber.onNext(null);
                }
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io())
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
                        adapter = new CommonAdapter<Map<String, Object>>(context, R.layout.recycle_item01_layout, datas) {
                            @Override
                            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                                holder.setText(R.id.id_recycle_item01_tv, stringObjectMap.get("text").toString());
                                Glide.with(context).load(stringObjectMap.get("id")).override(800, 800).centerCrop().into((ImageView) holder.getView(R.id.id_recycle_item01_im));
                            }
                        };
                        if(maps != null) {
                            initHeaderAndFooter(adapter);
                            initLoadMoreWrapper(mHeaderAndFooterWrapper);
                            recyclerView.setAdapter(mLoadMoreWrapper);
                            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    switch (position){
                                        case 5:
                                            Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                            break;
                                        case 6:
                                            Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                            break;
                                        case 7:
                                            Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                            break;
                                        case 8:
                                            Snackbar.make(view,"点击了"+position,Snackbar.LENGTH_SHORT).show();
                                            break;
                                        default:break;
                                    }
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });
                        }else {
                            mEmptyWrapper = new EmptyWrapper(adapter);
                            mEmptyWrapper.setEmptyView(LayoutInflater.from(context).inflate(R.layout.empty_view, recyclerView, false));
                            initHeaderAndFooter(mEmptyWrapper);
                            recyclerView.setAdapter(mHeaderAndFooterWrapper);
                        }
                    }
                });

    }

    private List<Map<String,Object>> initDatas() {
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
            datas.add(data);
        }

        return datas;
    }

    private void initLoadMoreWrapper(RecyclerView.Adapter adapter){
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(() -> new Handler().postDelayed(() -> {
            //进行数据更新的操作
//                for (int i = 0; i < 10; i++)
//                {
//                    mDatas.add("Add:" + i);
//                }
            mLoadMoreWrapper.notifyDataSetChanged();

        }, 1000));
    }
    private void initHeaderAndFooter(RecyclerView.Adapter adapter)
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        ImageView im1 = new ImageView(context);
        im1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,3));
        im1.setBackgroundResource(R.color.black);
        TextView t1 = new TextView(context);
        t1.setText("停车场推荐");
        t1.setTextSize(20);
        t1.setGravity(Gravity.CENTER);
        t1.setTextColor(getResources().getColor(R.color.black));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,15,0,15);
        t1.setLayoutParams(lp);
        ImageView im2 = new ImageView(context);
        im2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,3));
        im2.setBackgroundResource(R.color.black);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
        MyImageButton myImageButton01 = new MyImageButton(context);
        myImageButton01.setText("出租车位");
        myImageButton01.setTextColors(R.color.black);
        myImageButton01.setTextSize(15);
        myImageButton01.setPicture(R.drawable.rent);
        myImageButton01.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        myImageButton01.setOnClickListener(view -> Utils.showToast(context,"点击了出租车位"));
        MyImageButton myImageButton02 = new MyImageButton(context);
        myImageButton02.setText("附近美食");
        myImageButton02.setTextColors(R.color.black);
        myImageButton02.setTextSize(15);
        myImageButton02.setPicture(R.drawable.goodfood);
        myImageButton02.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        myImageButton02.setOnClickListener(view -> Utils.showToast(context,"点击了附近美食"));
        MyImageButton myImageButton03 = new MyImageButton(context);
        myImageButton03.setText("附近酒店");
        myImageButton03.setTextColors(R.color.black);
        myImageButton03.setTextSize(15);
        myImageButton03.setPicture(R.drawable.jiudian);
        myImageButton03.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        myImageButton03.setOnClickListener(view -> Utils.showToast(context,"点击了附近酒店"));
        MyImageButton myImageButton04 = new MyImageButton(context);
        myImageButton04.setText("附近加油站");
        myImageButton04.setTextColors(R.color.black);
        myImageButton04.setTextSize(15);
        myImageButton04.setPicture(R.drawable.addgas);
        myImageButton04.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        myImageButton04.setOnClickListener(view -> Utils.showToast(context,"点击了附近加油站"));
        layout.addView(myImageButton01);
        layout.addView(myImageButton02);
        layout.addView(myImageButton03);
        layout.addView(myImageButton04);
        MyRxViewPager myRxViewPager = new MyRxViewPager(context);
        myRxViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,350));
        myRxViewPager.getChildCount();
        myRxViewPager.getChildAt(0).setOnClickListener(view1 -> Utils.showToast(context,"点击了"));
        for (int i = 0; i < myRxViewPager.getChildCount(); i++) {
            final int finalI = i;
            myRxViewPager.getChildAt(i).setOnClickListener(view -> Utils.showToast(context,"点击了第"+ finalI +"张图片"));
        }
        mHeaderAndFooterWrapper.addHeaderView(myRxViewPager);
        mHeaderAndFooterWrapper.addHeaderView(layout);
        mHeaderAndFooterWrapper.addHeaderView(im1);
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addHeaderView(im2);

    }
}

