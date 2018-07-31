package com.example.zhangweikang.book_search.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhangweikang.book_search.R;

import java.util.ArrayList;

public class Info extends AppCompatActivity {

    private ArrayList list;
    private AnothorAdapter me;
    private RecyclerView rcy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent it2=getIntent();
        rcy=findViewById(R.id.lst);
        list=it2.getStringArrayListExtra("array");
        me=new AnothorAdapter(Info.this,list);
        rcy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcy.setItemAnimator(new DefaultItemAnimator());//设置适配器
        rcy.setAdapter(me);
    }
}
