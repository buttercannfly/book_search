package com.example.zhangweikang.book_search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AnothorAdapter extends RecyclerView.Adapter<AnothorAdapter.MyViewHolder>{
    private LayoutInflater layoutInflater;
    private List<String> lst;
    private Context context;

    public AnothorAdapter(Context context, List<String> lst){
        this.context=context;
        this.lst=lst;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_info, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(lst.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        MyViewHolder(View itemView){
            super(itemView);
            img=(itemView).findViewById(R.id.img);
        }
    }
}
