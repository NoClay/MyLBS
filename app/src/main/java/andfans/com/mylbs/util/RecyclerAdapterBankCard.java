package andfans.com.mylbs.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import andfans.com.mylbs.Data.ChePaiHao;
import andfans.com.mylbs.R;

/**
 * Created by 82661 on 2016/12/14.
 */

public class RecyclerAdapterBankCard extends RecyclerView.Adapter<RecyclerAdapterBankCard.ViewHolder>{
    private int layout;
    private Context context;
    private boolean isManage = false;
    private List<ChePaiHao> datas;
    private static final String TAG = "RecyclerAdapterChePaiHa";
    RecyclerAdapterChePaiHao.OnItemClickListener onItemClickListener = null;
    RecyclerAdapterChePaiHao.OnItemLongClickListener onItemLongClickListener = null;

    public RecyclerAdapterBankCard(int layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        public boolean onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(RecyclerAdapterChePaiHao.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(RecyclerAdapterChePaiHao.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
