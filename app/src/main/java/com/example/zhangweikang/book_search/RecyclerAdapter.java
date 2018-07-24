package com.example.zhangweikang.book_search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    Context context;
    private List<ForBean> mBean;

    public RecyclerAdapter(Context context,List<ForBean> mBean){
        this.mBean=mBean;
        this.context=context;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        MyViewHolder myHolder=new MyViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        ForBean bean=mBean.get(position);
        holder.tv_novlename.setText(bean.getNovelname());
        holder.tv_time.setText(bean.getTime());
        holder.tv_latest.setText(bean.getLatestname());
        holder.tv_author.setText(bean.getAuthorName());
        holder.pic.setImageBitmap(bean.getPic());


    }

    @Override
    public int getItemCount() {
        return mBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_novlename;
        TextView tv_latest;
        TextView tv_author;
        TextView tv_time;
        ImageView pic;
        public MyViewHolder(View inflate) {
            super(inflate);
            tv_author=inflate.findViewById(R.id.author_name);
            tv_latest=inflate.findViewById(R.id.latest_name);
            tv_time=inflate.findViewById(R.id.span_time);
            tv_novlename=inflate.findViewById(R.id.novel_name);
        }
    }
}
