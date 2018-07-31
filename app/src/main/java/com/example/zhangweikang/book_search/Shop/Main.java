package com.example.zhangweikang.book_search.Shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.zhangweikang.book_search.Book_index.MyAdapter;
import com.example.zhangweikang.book_search.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    private final String lock="lock";
    private static final String TAG ="MAIN" ;
    private MyAdapter mAdapter;
    private RecyclerView lst;
    private SwipeRefreshLayout srl;
    private Document document;
    private Document document1;
    private List<Goods> mgood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mgood=new ArrayList<>();
        lst=findViewById(R.id.lst);
        lst.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter=new MyAdapter(Main.this,mgood, new MyAdapter.onRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v, int position) {
                Intent main_intent = new Intent(Main.this,Show.class);
                Bundle bd = new Bundle();
                bd.putString("a",mgood.get(position).getUrl());
                main_intent.putExtras(bd);
                Dingdan.setName(mgood.get(position).getGood_name());
                Main.this.startActivity(main_intent);
            }
        });
        srl=findViewById(R.id.srl);
        lst.setItemAnimator(new DefaultItemAnimator());
//设置适配器
        lst.setAdapter(mAdapter);



        jsoupData();
        jsoupPic();

        srl.setColorSchemeColors(Color.RED, Color.YELLOW);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jsoupData();
                jsoupPic();
            }
        });
    }


    public void jsoupData(){
        srl.setRefreshing(true);
        mgood.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        document = Jsoup.connect("http://s.vancl.com/search?k=%E4%B8%8A%E8%A1%A3&orig=3")
                                .timeout(10000)
                                .get();

                        Elements noteList = document.select("div.pruarea.pruarea0124");
                        Elements li = noteList.select("li");
                        for (Element element : li) {
                            Goods good = new Goods();
                            good.setGood_name(element.select("a.track").text());//done
                            Elements lii = element.select("div.pic");
                            good.setUrl(lii.select("a").attr("abs:href"));//done
                            good.setSell(lii.select("div.teshui").text());
                            Elements liii = lii.select("a.track");
                            good.setPrice(element.select("span.Sprice").text());//done

                            mgood.add(good);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lst.setAdapter(mAdapter);
                                srl.setRefreshing(false);
                            }
                        });
                        lock.notify();
                    } catch (IOException e) {
                        Log.e("TAG", "error in jsoup");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        synchronized (lock) {
            try {
                lock.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void jsoupPic(){
        final int size=mgood.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<size;i++){
                    try{
                        document1= Jsoup.connect(mgood.get(i).getUrl()).timeout(10000).get();
                        mgood.get(i).setImage(document1.select("img#midimg").attr("abs:src"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lst.setAdapter(mAdapter);
                                srl.setRefreshing(false);
                            }
                        });
                    }catch(IOException e){
                        Log.e(TAG,"url connect failed");
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}