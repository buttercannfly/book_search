package com.example.zhangweikang.book_search;

//import android.renderscript.Element;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<ForBean> mBeans;
    private List<Page> mPage;
    private Document document;
    private Document document1;
    private Document document2;
    private SearchView mSearchView;
    private Button bt;

    public static String new_url = "https://www.gxwztv.com/search.htm?keyword=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bt1 =(Button)findViewById(R.id.bt1);
//        ed1 =(EditText)findViewById(R.id.ed1);
        mSearchView=findViewById(R.id.sh1);
        mSearchView.setIconifiedByDefault(true);
        bt=findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Robot.class);
                startActivity(intent);
            }
        });
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length()==0){
                    Toast.makeText(MainActivity.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                    Log.i("TAG","empty click");
                }
                else{
                    new_url=new_url+query;
                    mBeans = new ArrayList<>();
                    mPage = new ArrayList<Page>();
                    Intent main_intent = new Intent(MainActivity.this,ShowActivity.class);
                    Bundle bd = new Bundle();
                    bd.putString("a",new_url);
                    main_intent.putExtras(bd);
//                  jsoupPage(new_url);
                    new_url="https://www.gxwztv.com/search.htm?keyword=";
                    MainActivity.this.startActivity(main_intent);
//                    mSearchView.setQuery("",false);
//                    mSearchView.setFocusable(false);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new_url=new_url+ed1.getText();
//               Log.i(TAG, "onClick: "+new_url);
//                mBeans = new ArrayList<>();
//                mPage = new ArrayList<Page>();
//                Intent main_intent = new Intent(MainActivity.this,ShowActivity.class);
//                Bundle bd = new Bundle();
//                bd.putString("a",new_url);
//                main_intent.putExtras(bd);
////                jsoupPage(new_url);
//                new_url="https://www.gxwztv.com/search.htm?keyword=";
//                MainActivity.this.startActivity(main_intent);
//            }
//
//        });


    }

    public void jsoupData1(final String a){
        mBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Log.i(TAG, "run: "+a);
                    document = (Document) Jsoup.connect(a)
                            .timeout(10000)
                            .get();;
                    Elements noteList = document.select("div.panel-body");
                    Elements li = noteList.select("li.list-group-item.clearfix");
                    Log.i(TAG, "run:liNumber"+li.size());

                    for(Element element : li){
                        ForBean bean = new ForBean();
                        bean.setNovelname(element.select("div.col-xs-3").text());
                        Log.i(TAG, "小说名称:"+bean.getNovelname());
                        bean.setNovellink(element.select("a").attr("abs:href"));
                        Log.i(TAG, "小说链接: "+bean.getNovellink());
                        bean.setLatestname(element.select("div.col-xs-4").text());
                        Log.i(TAG, "最新章节:"+bean.getLatestname());
                        bean.setLatestlink(element.select("a").attr("abs:href"));
                        Log.i(TAG, "最新章节链接:"+bean.getLatestlink());
                        String b = element.select("span.time").text();
                        String c = element.select("div.col-xs-2").text();
                        c = c.substring(0,c.indexOf(b));
                        bean.setAuthorName(c);
//                        int temp = c.indexOf(b);
//                        Log.i(TAG, "run: " + temp);
                        Log.i(TAG, "作者名称:"+bean.getAuthorName());
                        bean.setTime(b);
                        Log.i(TAG, "发表时间: "+bean.getTime());

                        mBeans.add(bean);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.i(TAG,"end");
    }
    public void jsoupPage(final String a){
        mPage.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    document1 = (Document)Jsoup.connect(a)
                            .timeout(10000)
                            .get();
                    Elements noteList = document.select("ul.pagination.pagination-sm");
                    Elements li = noteList.select("li");

                    for(Element element : li){
                        Page page_former = new Page();
                        page_former.setPage(element.select("li").text());
                        Log.e(TAG,"page:"+page_former.getPage());
                        page_former.setPage_link(element.select("a").attr("abs:href"));
                        Log.e(TAG,"link:"+page_former.getPage_link());
                        mPage.add(page_former);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();


    }
//    public void jsoupText(final String a){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try{
//                    document2 = (Document)Jsoup.connect(a)
//                            .timeout(10000)
//                            .get();
//                }
//
//            }
//        })
//    }
//    public void show_nice(){
//        Intent main_intent = new Intent(MainActivity.this,ShowActivity.class);
//        MainActivity.this.startActivity(main_intent);
////        MainActivity.this.finish();
//   }

}
