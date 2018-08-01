package com.example.zhangweikang.book_search.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangweikang.book_search.R;
import com.example.zhangweikang.book_search.Shop.Dingdan;

public class Perchase extends AppCompatActivity {

    private TextView name;
    private TextView tel;
    private TextView addr;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perchase);
        Log.e("TAG","color:"+ Dingdan.getColor());
        Log.e("TAG","name:"+Dingdan.getName());
        Log.e("TAG","price:"+Dingdan.getPrice());
        Log.e("TAG","size:"+Dingdan.getSize());
        Log.e("TAG","user:"+Dingdan.getUser());
        name=findViewById(R.id.name);
        tel=findViewById(R.id.tel);
        addr=findViewById(R.id.addr);
        bt=findViewById(R.id.bt);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Perchase.this,Add_info.class);
                    startActivityForResult(intent,1);
            }
        });
        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perchase.this,Add_info.class);
                startActivityForResult(intent,1);
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perchase.this,Add_info.class);
                startActivityForResult(intent,1);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perchase.this,"perchase finished",Toast.LENGTH_SHORT).show();
            }
        });

    }


    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            Bundle bd=data.getExtras();
            name.setText(bd.getString("name"));
            tel.setText(bd.getString("tel"));
            addr.setText(bd.getString("addr"));
            Dingdan.setT_name(name.getText().toString());
            Dingdan.setT_addr(addr.getText().toString());
            Dingdan.setT_tel(tel.getText().toString());
        }
    }
}
