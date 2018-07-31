package com.example.zhangweikang.book_search.Log_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhangweikang.book_search.Shop.Dingdan;
import com.example.zhangweikang.book_search.MainActivity;
import com.example.zhangweikang.book_search.R;

public class Login extends AppCompatActivity {

    private Button bt;
    private Button bt2;
    private EditText ed1;
    private EditText ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed1.getText().toString().trim();
                String pass = ed2.getText().toString().trim();
                //userList=SqliteDB.getInstance(getApplicationContext()).loadUser();
                int result = SqliteDB.getInstance(getApplicationContext()).Quer(pass, name);
                if (result == 1) {
                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    Dingdan.setUser(name);
                    startActivity(intent);
                } else if (result == 0) {
                    Toast.makeText(Login.this, "用户名不存在！", Toast.LENGTH_SHORT).show();

                } else if (result == -1) {
                    Toast.makeText(Login.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,register.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
