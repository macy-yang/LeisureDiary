package com.scujcc.leisurediary.ui.diarylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scujcc.leisurediary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器
 *
 * @author 杨梦婷
 * time:2022/11/17
 */
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.HomeViewHolder> {

    private Context mContext;
    private List<Recommend> recommendList = new ArrayList<>();

    public DiaryListAdapter(Context context, List<Recommend> recommendList) {
        this.mContext = context;
        this.recommendList = recommendList;
    }

    //创建列表组件
    @NonNull
    @Override
    public DiaryListAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diarylist_design, null);
        return new DiaryListAdapter.HomeViewHolder(view, this);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.imageIdIv.setImageResource(recommendList.get(position).getImageId());
        holder.nameTv.setText(recommendList.get(position).getName());
    }

    //返回列表数据总数
    @Override
    public int getItemCount() {
        return recommendList.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView imageIdIv;
        private RecyclerView.Adapter adapter;

        public HomeViewHolder(@NonNull View itemView, RecyclerView.Adapter adapter) {
            super(itemView);
            this.nameTv = itemView.findViewById(R.id.home_item_text);
            this.imageIdIv = itemView.findViewById(R.id.home_item_mage);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(view, recommendList.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, Recommend data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}