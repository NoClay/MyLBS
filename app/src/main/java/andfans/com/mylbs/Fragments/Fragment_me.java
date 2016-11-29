package andfans.com.mylbs.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import andfans.com.mylbs.Activity.LoginActivity;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.RecycleAdapter_me;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 * Created by 兆鹏 on 2016/11/23.
 */
public class Fragment_me extends Fragment {
    private RecyclerView recyclerView;
    private List<Map<String,Object>> datas;
    private RecycleAdapter_me adapterMe;
    private LinearLayoutManager manager;
    private Context context;
    private ImageView im_pic;
    private Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment03_layout,container,false);
        context = getContext();
        init(v);
        return v;
    }

    private void init(View v) {
        im_pic = (ImageView) v.findViewById(R.id.id_me_im_user_pic);
        im_pic.setOnClickListener(view -> {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.id_me_rv);
        manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        datas = new ArrayList<>();
        Observable.create((Observable.OnSubscribe<List<Map<String, Object>>>) subscriber -> {
            int [] pic = {R.drawable.personalinformation,R.drawable.transactionrecord,R.drawable.collections,R.drawable.bankcard};
            String [] text = {"我的信息","我的交易记录","我的收藏","我的银行卡"};
            for (int i = 0; i < text.length; i++) {
                Map<String,Object> data = new HashMap<>();
                data.put("text",text[i]);
                data.put("pic",pic[i]);
                data.put("back",R.drawable.backright);
                datas.add(data);
            }
            subscriber.onNext(datas);
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
                        adapterMe = new RecycleAdapter_me(context,maps,R.layout.recycle_item02_layout);
                        recyclerView.setAdapter(adapterMe);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
                        adapterMe.setOnItemClickListener((view, position) -> {
                            switch (position){
                                case 0:

                                    break;
                                case 1:

                                    break;
                                case 2:

                                    break;
                                case 3:

                                    break;
                                default:break;
                            }
                        });
                    }
                });
    }
}
