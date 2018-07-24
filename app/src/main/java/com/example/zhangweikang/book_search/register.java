package com.example.zhangweikang.book_search;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

    private List<User> userList;
    private List<User> dataList = new ArrayList<>();
    private Context mContext;
    private Button bt;
    private EditText ed1;
    private EditText ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bt=findViewById(R.id.bt);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=ed1.getText().toString().trim();
                String pass=ed2.getText().toString().trim();

                User user=new User();
                user.setUsername(name);
                user.setUserpwd(pass);

                int result=SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                if (result==1){
                    Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(register.this,Login.class);
                    startActivity(intent);
                }else  if (result==-1)
                {
                    Toast.makeText(register.this,"用户名已经存在！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                }



            }
        });
    }
}

