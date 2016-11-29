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
public class RecycleAdapter_me extends RecyclerView.Adapter<RecycleAdapter_me.ViewHolder> {
    private List<Map<String,Object>> datas = new ArrayList<>();
    private OnItemClickListener onItemClickListener = null;
    private int layout;
    private Context context;

    public RecycleAdapter_me(Context context, List<Map<String, Object>> datas, int layout) {
        this.datas = datas;
        this.layout = layout;
        this.context = context;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
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
        holder.im_back.setImageResource((Integer) datas.get(position).get("back"));
        holder.im_pic.setImageResource((Integer) datas.get(position).get("pic"));
        holder.itemView.setTag(datas.get(position));

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
        private ImageView im_pic;
        private ImageView im_back;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_recycle_item02_tv);
            im_pic = (ImageView) itemView.findViewById(R.id.id_recycle_item02_pic);
            im_back = (ImageView) itemView.findViewById(R.id.id_recycle_item02_back);
        }
    }
}


