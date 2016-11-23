package andfans.com.mylbs.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import andfans.com.mylbs.R;

/*
 * Created by 兆鹏 on 2016/11/23.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<Map<String,Object>> datas = new ArrayList<>();
    private OnItemClickListener onItemClickListener = null;
    private int layout;
    private Context context;

    public RecycleAdapter(Context context,List<Map<String, Object>> datas, int layout) {
        this.datas = datas;
        this.layout = layout;
        this.context = context;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(datas.get(position).get("text").toString());
        Glide.with(context).load(datas.get(position).get("id")).override(800,800).centerCrop().into(holder.im);
        holder.itemView.setTag(datas.get(position));
        //holder.im.setImageURI("http://pic.anhuinews.com/0/03/16/30/3163049_744230.jpg");
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v,holder.getLayoutPosition()));
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private ImageView im;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_recycle_item01_tv);
            im = (ImageView) itemView.findViewById(R.id.id_recycle_item01_im);
        }
    }
}


