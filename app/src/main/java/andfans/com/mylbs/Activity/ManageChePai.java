package andfans.com.mylbs.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import andfans.com.mylbs.Data.ChePaiHao;
import andfans.com.mylbs.PopWindow.EditChePaiHaoPopWindow;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.RecyclerAdapterChePaiHao;
import andfans.com.mylbs.util.UtilClass;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static android.R.attr.dashGap;
import static android.R.attr.type;

public class ManageChePai extends AppCompatActivity {

    RecyclerAdapterChePaiHao adapter;
    RecyclerView recyclerView;
    List<ChePaiHao> datas;
    PopupWindow editChePaiHao;
    ChePaiHao temp;
    private boolean flag = false;
    private Context context = this;
    private static final String TAG = "ManageChePai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_che_pai);
        initData();
        findView();
    }

    private void findView() {
        ((TextView) findViewById(R.id.info_title)).setText("管理我的车牌号");
        findViewById(R.id.back).setOnClickListener(v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.chepaihaoRecyclerView);
        adapter = new RecyclerAdapterChePaiHao(R.layout.recyclerview_chepaihao_item, this, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListener((view, position) ->
                editChePaiHaoPopupWindow(datas.get(position), position, true));
        adapter.setOnItemLongClickListener((view1, position) -> {
            Dialog.OnClickListener listener = (dialogInterface, i) -> {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:{
                        Toast.makeText(context, "正在删除", Toast.LENGTH_SHORT).show();
                        //删除
                        datas.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                    }
                    case DialogInterface.BUTTON_NEGATIVE:{
                        break;
                    }
                }
            };
            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle("是否删除？")
                    .setMessage(datas.get(position).getNumber())
                    .setPositiveButton("确定", listener)
                    .setNegativeButton("取消",listener)
                    .create();
            dialog.show();
            return true;
        });
        findViewById(R.id.add).setOnClickListener(v ->
            editChePaiHaoPopupWindow(null, -1, false));
        findViewById(R.id.manage).setOnClickListener(v -> adapter.toggleManage());
    }

    public void editChePaiHaoPopupWindow(ChePaiHao src, int position, boolean isEdit){
        temp = new ChePaiHao();
        if (src != null){
            temp.setNumber(src.getNumber());
            temp.setOwner(src.getOwner());
            temp.setOtherName(src.getOtherName());
        }
        View contentView = LayoutInflater.from(context)
                .inflate(R.layout.edit_chepaihao, null);
        editChePaiHao = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        EditText[] views = new EditText[3];
        views[0] = (EditText) contentView.findViewById(R.id.editOtherName);
        views[1] = (EditText) contentView.findViewById(R.id.editOwnerName);
        views[2] = (EditText) contentView.findViewById(R.id.editNumber);
        views[0].setText(temp.getOtherName());
        views[1].setText(temp.getOwner());
        views[2].setText(temp.getNumber());
        editChePaiHao.setBackgroundDrawable(new ColorDrawable(0x88000000));
        editChePaiHao.setFocusable(true);
        editChePaiHao.setAnimationStyle(R.style.PopupAnimation);
        editChePaiHao.showAtLocation(findViewById(R.id.activity_manage_che_pai),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        contentView.findViewById(R.id.cancelBtn)
                .setOnClickListener(view -> {
                    editChePaiHao.dismiss();
                    temp = null;
                });
        contentView.findViewById(R.id.okayBt).setOnClickListener(view1 -> {
            if (views[0].getText().toString().isEmpty()){
                UtilClass.toToast(context, "备注不能为空");
            }else if (views[1].getText().toString().isEmpty()){
                UtilClass.toToast(context, "车主不能为空");
            }else if (views[2].getText().toString().isEmpty()){
                UtilClass.toToast(context, "车牌号不能为空");
            }else{
                temp.setOtherName(views[0].getText().toString());
                temp.setOwner(views[1].getText().toString());
                temp.setNumber(views[2].getText().toString());
                editChePaiHao.dismiss();
                if (isEdit){
                    datas.get(position).setOtherName(temp.getOtherName());
                    datas.get(position).setOwner(temp.getOwner());
                    datas.get(position).setNumber(temp.getNumber());
                }else{
                    datas.add(temp);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChePaiHao chePaiHao = new ChePaiHao();
            chePaiHao.setOtherName("王的座驾" + i);
            chePaiHao.setOwner("尼古拉斯赵四");
            chePaiHao.setNumber("豫A:"
                    + new Random().nextInt(9)
                    + new Random().nextInt(9)
                    + new Random().nextInt(9)
                    + new Random().nextInt(9)
                    + new Random().nextInt(9)
            );
            datas.add(chePaiHao);
        }
    }
}
