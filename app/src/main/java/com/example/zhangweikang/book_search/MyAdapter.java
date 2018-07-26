package com.example.zhangweikang.book_search;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ZHANGWEIKANG on 2018/5/19.
 */

public class MyAdapter extends BaseAdapter {

    private List<ForBean> mBean;
    private LayoutInflater inflater;
    public MyAdapter(){}

    public MyAdapter(List<ForBean> mBean, Context context){
        this.mBean = mBean;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount(){
        return mBean == null?0:mBean.size();
    }

    @Override
    public ForBean getItem(int position){
        return mBean.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ForBean mbean = getItem(position);
        ViewHolder holder=null;
        if(convertView==null){
            convertView =inflater.inflate(R.layout.item_home,null);
            holder=new ViewHolder();
            holder.pic=(ImageView) convertView.findViewById(R.id.pic);
            holder.tv_author= (TextView) convertView.findViewById(R.id.author_name);
            holder.tv_latest=(TextView)convertView.findViewById(R.id.latest_name);
            holder.tv_novlename=(TextView) convertView.findViewById(R.id.novel_name);
            holder.tv_time=(TextView) convertView.findViewById(R.id.span_time);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.pic.setImageBitmap(mbean.getPic());
        holder.tv_time.setText(mbean.getTime());
        holder.tv_novlename.setText(mbean.getNovelname());
        holder.tv_latest.setText(mbean.getLatestname());
        holder.tv_author.setText(mbean.getAuthorName());
        return convertView;
    }
    public void add(){
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView pic;
        TextView tv_author;
        TextView tv_latest;
        TextView tv_time;
        TextView tv_novlename;
    }
}
