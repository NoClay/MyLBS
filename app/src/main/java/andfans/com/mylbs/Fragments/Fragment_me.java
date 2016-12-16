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

import andfans.com.mylbs.Activity.ChangeMyDataActivity;
import andfans.com.mylbs.Activity.ManageBankCard;
import andfans.com.mylbs.Activity.ManageChePai;
import andfans.com.mylbs.LoginAndSign.LoginActivity;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.RecycleAdapter_me;
import andfans.com.mylbs.util.UtilClass;
import andfans.com.mylbs.util.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESULT;

/*
 * Created by 兆鹏 on 2016/11/23.
 */
public class Fragment_me extends Fragment {
    private static final int REQUEST_CHANGE_DATA = 0;
    private RecyclerView recyclerView;
    private List<Map<String, Object>> datas;
    private RecycleAdapter_me adapterMe;
    private LinearLayoutManager manager;
    private Context context;
    private ImageView im_pic;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment03_layout, container, false);
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
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        datas = new ArrayList<>();
        Observable.create((Observable.OnSubscribe<List<Map<String, Object>>>) subscriber -> {
            int[] pic = {R.drawable.personalinformation,
                    R.drawable.chepaihao,
                    R.drawable.transactionrecord,
                    R.drawable.collections,
                    R.drawable.bankcard};
            String[] text = {"我的信息", "我的车牌号", "我的交易记录", "我的收藏", "我的银行卡"};
            for (int i = 0; i < text.length; i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("text", text[i]);
                data.put("pic", pic[i]);
                data.put("back", R.drawable.backright);
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
                        adapterMe = new RecycleAdapter_me(context, maps, R.layout.recycle_item02_layout);
                        recyclerView.setAdapter(adapterMe);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                        adapterMe.setOnItemClickListener((view, position) -> {
                            //点击修改信息， 判断是否登录
                            if (!Utils.isLogined) {
                                UtilClass.toToast(getContext(), "您还没有登录");
                                return;
                            }
                            switch (position) {
                                case 0:
                                    Intent intent = new Intent(getContext()
                                            , ChangeMyDataActivity.class);
                                    startActivityForResult(intent, REQUEST_CHANGE_DATA);
                                    break;
                                case 1:
                                    //此处管理我的车牌号
                                    Intent intent1 = new Intent(getContext(), ManageChePai.class);
                                    startActivity(intent1);
                                    break;
                                case 2:

                                    break;
                                case 3:

                                    break;
                                case 4:
                                    //管理银行卡
                                    Intent intent2 = new Intent(getContext(), ManageBankCard.class);
                                    startActivity(intent2);
                                    break;
                            }
                        });
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHANGE_DATA: {
                if (requestCode == RESULT_OK) {
                    //ps：修改信息，此处是否重新， 退出后，也会进行修改
                }
            }
        }
    }
}
