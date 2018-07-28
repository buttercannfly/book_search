package com.example.zhangweikang.book_search;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private final String lock="lock";
    private static final String TAG = "ShowActivity";
    private List<ForBean> mBeans;
    private List<Page> mPage;
    private List<Nextpage> qq;
    private Button bt;
    private Document document;
    private ListView list_item;
    private  String page_url;
    private MyAdapter me;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent it2 = getIntent();
        Bundle bd = it2.getExtras();
        if(bd != null){
            page_url=bd.getString("a");
            Log.i(TAG,"new url:"+page_url);
            mBeans = new ArrayList<>();
            mPage = new ArrayList<Page>();
            qq = new ArrayList<Nextpage>();
            jsoupData1(page_url);
//            for(int i=0;i<mBeans.size();i++){
//                if(mBeans.get(i).getPic()==null) {
//                    jsoupGet(i);//获取书籍封面
//                }
//            }

            mHandler=new Handler(){
                @Override
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    me.notifyDataSetChanged();
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int i=0;i<mBeans.size();i++){
                            if(mBeans.get(i).getPic()==null){
                                String pic_nice="";
                                Bitmap pic=null;
                                document = (Document) Jsoup.connect(mBeans.get(i).getNovellink()).timeout(10000).get();
                                Elements noteList = document.select("div.panel-body");
                                Elements li = noteList.select("div.col-xs-8");
                                Elements lii = li.select("div.panel.panel-default.mt20");
                                Elements liii = lii.select("div.panel-body");
                                pic_nice += document.select("img.img-thumbnail").attr("abs:src");
                                Log.e(TAG,"pic_url:"+pic_nice);
                                pic=getBitmap(pic_nice);
                                mBeans.get(i).setPic(pic);
                                Message message = new Message();
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                    catch(Exception e){
                        Log.e("TAG","im out");
                        e.printStackTrace();
                    }
                }
            }).start();


            list_item = (ListView) findViewById(R.id.lllist);
            me = new MyAdapter(mBeans,ShowActivity.this);
            list_item.setAdapter(me);
            list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent main_intent = new Intent(ShowActivity.this,Webview.class);
                    Bundle bd = new Bundle();
                    bd.putString("a",mBeans.get(position).getNovellink());
                    main_intent.putExtras(bd);
                    ShowActivity.this.startActivity(main_intent);
                }
            });
        }


//        bt=findViewById(R.id.bt1);//按钮点击刷新界面，后期去掉
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i=0;i<mBeans.size();i++){
//                    if(mBeans.get(i).getPic()==null) {
//                        jsoupGet(i);//获取书籍封面
//                        me.notifyDataSetChanged();
//                        list_item.setAdapter(me);
//                    }
//                }
//
//            }
//        });


    }

    Runnable add=new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<mBeans.size();i++){
                String pic_nice="";
                Bitmap pic=null;
                try {
                    Nextpage ll = new Nextpage();
                    document = (Document) Jsoup.connect(mBeans.get(i).getNovellink()).timeout(10000).get();
                    Elements noteList = document.select("div.panel-body");
                    Elements li = noteList.select("div.col-xs-8");
                    Elements lii = li.select("div.panel.panel-default.mt20");
                    Elements liii = lii.select("div.panel-body");
                    pic_nice += document.select("img.img-thumbnail").attr("abs:src");
//                    Log.e(TAG,"pic_url:"+pic_nice);
                    pic=getBitmap(pic_nice);
                    mBeans.get(i).setPic(pic);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                me.notifyDataSetChanged();
            }
        }
    };




    public void jsoupData1(final String a){
        mBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
            synchronized (lock) {
                try {
//                    Log.i(TAG, "run: " + a);
                    document = (Document) Jsoup.connect(a)
                            .timeout(10000)
                            .get();
                    Elements noteList = document.select("div.panel-body");
                    Elements li = noteList.select("li.list-group-item.clearfix");
//                    Log.i(TAG, "run:liNumber" + li.size());

                    for (Element element : li) {
                        ForBean bean = new ForBean();
                        bean.setNovelname(element.select("div.col-xs-3").text());
//                        Log.i(TAG, "小说名称:" + bean.getNovelname());
                        bean.setNovellink(element.select("a").attr("abs:href"));
//                        Log.i(TAG, "小说链接: " + bean.getNovellink());
//                        jsoupGet(bean.getNovellink(),bean);
//                        bean=jsoupGet(bean.getNovellink(),bean);
                        bean.setLatestname(element.select("div.col-xs-4").text());
//                        Log.i(TAG, "最新章节:" + bean.getLatestname());
                        bean.setLatestlink(element.select("a").attr("abs:href"));
//                        Log.i(TAG, "最新章节链接:" + bean.getLatestlink());
                        String b = element.select("span.time").text();
                        String c = element.select("div.col-xs-2").text();
                        c = c.substring(0, c.indexOf(b));
                        bean.setAuthorName(c);
//                        Log.i(TAG, "作者名称:" + bean.getAuthorName());
                        bean.setTime(b);
//                        Log.i(TAG, "发表时间: " + bean.getTime());
                        if(bean.getNovelname().equals("小说名称")== false){
                            mBeans.add(bean);
                        }
                    }
                    lock.notify();

                } catch (IOException e) {
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


    public void jsoupPage(final String a){
        mPage.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        document = (Document) Jsoup.connect(a)
                                .timeout(10000)
                                .get();
                        Elements noteList = document.select("ul.pagination.pagination-sm");
                        Elements li = noteList.select("li");

                        for (Element element : li) {
                            Page page_former = new Page();
                            page_former.setPage(element.select("li").text());
                            Log.i(TAG, "page" + page_former.getPage());
                            page_former.setPage_link(element.select("a").attr("abs:href"));
                            Log.i(TAG, "link" + page_former.getPage_link());
                            mPage.add(page_former);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

        public void jsoupGet(final int i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String pic_nice="";
                    Bitmap pic=null;
                        try {
                            Nextpage ll = new Nextpage();
                            document = (Document) Jsoup.connect(mBeans.get(i).getNovellink()).timeout(10000).get();
                            Elements noteList = document.select("div.panel-body");
                            Elements li = noteList.select("div.col-xs-8");
                            Elements lii = li.select("div.panel.panel-default.mt20");
                            Elements liii = lii.select("div.panel-body");
                            pic_nice += document.select("img.img-thumbnail").attr("abs:src");
                            Log.e(TAG,"pic_url:"+pic_nice);
                            pic=getBitmap(pic_nice);
                            mBeans.get(i).setPic(pic);
                        }
                        catch (Exception e) {
                            Log.e(TAG,"oh,i'm out?????");
                            e.printStackTrace();
                        }
                }
            }).start();
        }





    public Bitmap getBitmap(String path) throws IOException{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }

        Resources res= getResources();
        Bitmap bmp =BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);

        return bmp;
    }
}

