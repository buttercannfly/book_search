package com.example.zhangweikang.book_search.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangweikang.book_search.Book_index.ForBean;
import com.example.zhangweikang.book_search.R;

import java.util.List;

public class TrAdapter extends BaseAdapter {
    private List<List_item> list_items;
    private Context context;
    private LayoutInflater inflater;

    public TrAdapter(){}

    public TrAdapter(List<List_item> list_items, Context context){
        this.list_items = list_items;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list_items.size();
    }

    @Override
    public Object getItem(int position) {
        return list_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List_item list_item= (List_item) getItem(position);
        Hold holder=null;
        if(convertView==null){
            convertView =inflater.inflate(R.layout.it_home,null);
            holder=new Hold();
            holder.name=(TextView) convertView.findViewById(R.id.name);
            holder.tel= (TextView) convertView.findViewById(R.id.tel);
            holder.addr=(TextView)convertView.findViewById(R.id.addr);
            convertView.setTag(holder);
        }
        else{
            holder=(Hold)convertView.getTag();
        }
        holder.name.setText(list_item.getI_name());
        holder.tel.setText(list_item.getI_tel());
        holder.addr.setText(list_item.getI_addr());
        return convertView;

    }


    private class Hold{
        TextView name;
        TextView tel;
        TextView addr;
    }
}
