package com.example.zhangweikang.book_search.AI;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangweikang.book_search.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends AppCompatActivity {
    private MyAdapter myAdapter;
    private ArrayList<Msg> msgs;
    private EditText et_input;
    private ListView lv;
    public static String real_answer="";
    private final String lock="lock";
    private EditText ed;
    private Button bt;
    private TextView tx;
    public String finalanswer;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        lv = (ListView) findViewById(R.id.lv);
        et_input = (EditText) findViewById(R.id.et_input);
        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_input.getText().toString();
                if (!content.isEmpty()) {
                    msgs.add(new Msg(content, Msg.TYPE_SEND));
                    myAdapter.notifyDataSetChanged();



                    Constant.Demo_智能问答.智能回复接口HttpsTest(et_input.getText().toString());
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            synchronized (lock) {
                                finalanswer = Constant.mAnswer;
                                if (finalanswer != null) {
                                    Log.e("TAG:::::::::::", finalanswer);
                                }
                                lock.notify();
                            }
                        }
                    };
                    Timer timer =new Timer();
                    timer.schedule(task,800);
                    synchronized (lock) {
                        try {
                            lock.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    parseJson(Constant.mAnswer);

                    mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg){
                            super.handleMessage(msg);
                            Log.e("HANDLE","9999999");
                            msgs.add(new Msg(real_answer, Msg.TYPE_RECEIVE));
                            myAdapter.notifyDataSetChanged();
                        }
                    };
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            parseJson(Constant.mAnswer);
                            Message message = new Message();
                            mHandler.sendMessage(message);
                        }
                    }).start();






                    lv.setSelection(msgs.size() - 1);
                    et_input.setText("");
                } else {
                    Toast.makeText(Robot.this, "请输入内容!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        msgs = new ArrayList<>();
//        msgs.add(new Msg("hello", Msg.TYPE_RECEIVE));
//        msgs.add(new Msg("who is that?", Msg.TYPE_SEND));
//        msgs.add(new Msg("this is LiLei,nice to meet you!", Msg.TYPE_RECEIVE));

        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Msg getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.listview_item, null);
                holder.tv_receive = (TextView) convertView.findViewById(R.id.tv_receive);
                holder.tv_send = (TextView) convertView.findViewById(R.id.tv_send);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Msg msg = getItem(position);
            if (msg.type == Msg.TYPE_RECEIVE) {
                holder.tv_receive.setVisibility(View.VISIBLE);
                holder.tv_send.setVisibility(View.GONE);
                holder.tv_receive.setText(msg.content);
            } else if (msg.type == Msg.TYPE_SEND) {
                holder.tv_send.setVisibility(View.VISIBLE);
                holder.tv_receive.setVisibility(View.GONE);
                holder.tv_send.setText(msg.content);
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_receive;
        TextView tv_send;
    }













//        bt=findViewById(R.id.bt1);
//        ed=findViewById(R.id.ed);
//        tx=findViewById(R.id.tx1);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Demo_智能问答.智能回复接口HttpsTest(ed.getText().toString());
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        synchronized (lock) {
//                            finalanswer = Constant.mAnswer;
//                            if (finalanswer != null) {
//                                Log.e("TAG:::::::::::", finalanswer);
//                            }
//                            lock.notify();
//                        }
//                    }
//                };
//                Timer timer =new Timer();
//                timer.schedule(task,800);
//                synchronized (lock) {
//                    try {
//                        lock.wait();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
////                parseJson(Constant.mAnswer);
//
//                mHandler=new Handler(){
//                    @Override
//                    public void handleMessage(Message msg){
//                        super.handleMessage(msg);
//                        tx.setText(real_answer);
//                    }
//                };
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        parseJson(Constant.mAnswer);
//                        Message message = new Message();
//                        mHandler.sendMessage(message);
//                    }
//                }).start();
//            }
//        });


    private void initdata(){

    }

    //json转化，提取content
    private void parseJson(String json){
        try{

            JSONObject jsonObject = (JSONObject) new JSONObject(json);
            JSONObject jsonObject1=(JSONObject)jsonObject.getJSONObject("result");
            real_answer=jsonObject1.getString("content");
        }
        catch(Exception e){
            Log.e("NNNNNN","OOOOOOOO");
            e.printStackTrace();
        }
    }
}

