package andfans.com.mylbs.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import andfans.com.mylbs.Data.ChePaiHao;
import andfans.com.mylbs.R;

/**
 * Created by 82661 on 2016/12/11.
 */

public class RecyclerAdapterChePaiHao extends RecyclerView.Adapter<RecyclerAdapterChePaiHao.ViewHolder>{
    private int layout;
    private Context context;
    private boolean isManage = false;
    private List<ChePaiHao> datas;
    private static final String TAG = "RecyclerAdapterChePaiHa";
    OnItemClickListener onItemClickListener = null;
    OnItemLongClickListener onItemLongClickListener = null;


    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        public boolean onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerAdapterChePaiHao(int layout, Context context, List<ChePaiHao> datas) {
        this.layout = layout;
        this.context = context;
        this.datas = datas;
    }

    /**
     * 切换管理状态
     */
    public void toggleManage(){
        isManage = !isManage;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChePaiHao chePaiHao = datas.get(position);
        if (isManage){
            holder.deleteButton.setOnClickListener(view -> {
                datas.remove(chePaiHao);
                this.notifyItemRemoved(position);
                Log.d(TAG, "onBindViewHolder: delete" + chePaiHao.getOtherName());
                if (position != datas.size()){
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
            holder.deleteButton.setVisibility(View.VISIBLE);
        }else{
            holder.deleteButton.setVisibility(View.GONE);
        }
        holder.otherNameView.setText("备注：" + chePaiHao.getOtherName());
        holder.ownerNameView.setText("车主：" + chePaiHao.getOwner());
        holder.chePaiHaoView.setText("车牌号：" + chePaiHao.getNumber());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, position));
        holder.itemView.setOnLongClickListener(view1 -> {
            return onItemLongClickListener.onItemLongClick(view1, position);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView otherNameView;
        TextView ownerNameView;
        TextView chePaiHaoView;
        Button deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            otherNameView = (TextView) itemView.findViewById(R.id.otherName);
            ownerNameView = (TextView) itemView.findViewById(R.id.owner);
            chePaiHaoView = (TextView) itemView.findViewById(R.id.number);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
