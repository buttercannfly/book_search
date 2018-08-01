package com.example.zhangweikang.book_search.Shop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangweikang.book_search.R;

import java.util.ArrayList;

public class Explicit extends AppCompatActivity {

    private Button bt;
    private TextView name;
    private TextView tel;
    private TextView addr;
    private TextView addr1;
    private SQLiteDatabase db;
    private OrderDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        bind();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Explicit.this,Add_info.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",name.getText().toString());
                bundle.putString("tel",addr.getText().toString());
                bundle.putString("addr",addr.getText().toString()+addr1.getText().toString());
                intent.putExtras(bundle);
                myDBHelper = new OrderDBHelper(Explicit.this);
                db = myDBHelper.getWritableDatabase();

                db.beginTransaction();

                db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + "(Id , Name ,Tel ,Addr) values ("+Dingdan.getUser()+", "+Dingdan.getT_name()+", "+Dingdan.getT_tel()+", "+Dingdan.getT_addr()+")");

                db.setTransactionSuccessful();
                db.close();
                Explicit.this.setResult(1,intent);
                Explicit.this.finish();
            }
        });
    }
    public void bind(){
        bt=findViewById(R.id.bt);
        name=findViewById(R.id.name);
        tel=findViewById(R.id.tel);
        addr=findViewById(R.id.addr);
        addr1=findViewById(R.id.addr1);
    }
}
