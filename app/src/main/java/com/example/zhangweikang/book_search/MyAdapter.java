package com.example.zhangweikang.book_search;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
    private List<Goods> mgoods;
    private Context context;
    private onRecyclerViewItemClick mOnRvItemClick;
    public MyAdapter(Context context, List<Goods> mgoods, onRecyclerViewItemClick onRvItemClick){
        this.mgoods=mgoods;
        this.mOnRvItemClick = onRvItemClick;
        this.context=context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hom,parent,false);
        MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mText.setText(mgoods.get(position).getGood_name());
        Glide.with(context).load(mgoods.get(position).getImage())
                .into(holder.img);
        holder.pri.setText(mgoods.get(position).getPrice());
        holder.pri_now.setText(mgoods.get(position).getSell());
    }

    @Override
    public int getItemCount() {
        return mgoods.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mText;
        ImageView img;
        TextView pri;
        TextView pri_now;
        ViewHolder(View itemView) {
            super(itemView);
            mText=itemView.findViewById(R.id.tx1);
            img=itemView.findViewById(R.id.img);
            pri=itemView.findViewById(R.id.tx2);
            pri_now=itemView.findViewById(R.id.tx3);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mOnRvItemClick != null)
                mOnRvItemClick.onItemClick(view, getAdapterPosition());
        }
    }
    public interface onRecyclerViewItemClick {
        void onItemClick(View v, int position);
    }
}
