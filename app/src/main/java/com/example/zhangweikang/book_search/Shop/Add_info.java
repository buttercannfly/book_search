package com.example.zhangweikang.book_search.Shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhangweikang.book_search.R;

import java.util.ArrayList;

public class Add_info extends AppCompatActivity {

    private String name;
    private String tel;
    private String addr;
    private ListView lst;
    private Button bt;
    private ArrayList<List_item> list_add;
    private TrAdapter tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        list_add=new ArrayList<>();
        lst=findViewById(R.id.lst) ;
        bt=findViewById(R.id.bt);
        lst=findViewById(R.id.lst);
        find_sql();
        tr=new TrAdapter(list_add,Add_info.this);
        lst.setAdapter(tr);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent main_intent=new Intent(Add_info.this,Perchase.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",list_add.get(position).getI_name());
                bundle.putString("tel",list_add.get(position).getI_tel());
                bundle.putString("addr",list_add.get(position).getI_addr());
                main_intent.putExtras(bundle);
                Add_info.this.setResult(1,main_intent);
                Add_info.this.finish();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_info.this,Explicit.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.e("EEEEEEEEEEE","OOOOOOOOOOOO");
        if(requestCode==1){
            Log.e("TUUY","&**"+"code ok");
            Bundle bd=data.getExtras();
            name = bd.getString("name");
            Log.e("^&^^^", "haha:" + name);
                tel = bd.getString("tel");
                Log.e("^&^^^", "haha:" + tel);
                addr = bd.getString("addr");
                Log.e("^&^^^", "haha:" + addr);
                List_item list_item=new List_item();
                list_item.setI_name(name);
                list_item.setI_tel(tel);
                list_item.setI_addr(addr);
                list_add.add(list_item);
                Log.e("TIANXIU","lalala"+list_add.size());
                tr=new TrAdapter(list_add,Add_info.this);
                tr.notifyDataSetChanged();
                lst.setAdapter(tr);
        }
    }

    public void find_sql(){

    }
}
