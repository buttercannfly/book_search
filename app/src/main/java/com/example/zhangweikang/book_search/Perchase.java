package com.example.zhangweikang.book_search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Perchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perchase);
        Log.e("TAG","ha:"+Dingdan.getColor());
        Log.e("TAG","ha:"+Dingdan.getName());
        Log.e("TAG","ha:"+Dingdan.getPrice());
        Log.e("TAG","ha:"+Dingdan.getSize());
    }
}
