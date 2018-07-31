package com.example.zhangweikang.book_search.Shop;

import android.content.Intent;
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
                startActivity(intent);
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
