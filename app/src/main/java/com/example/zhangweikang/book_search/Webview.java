package com.example.zhangweikang.book_search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toolbar;



public class Webview extends AppCompatActivity {

    private static final String TAG = "Webview";
    private String pg;
    private long exitTime = 0;
    private Button bt;
    private WebView mWvShow;
    private Toolbar mTbShow;
    boolean blockLoadingNetworkImage=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent it2 = getIntent();
        Bundle bd = it2.getExtras();
        pg=bd.getString("a");
        Log.i(TAG,"new url:"+pg);
        mWvShow = (WebView)findViewById(R.id.web);
        mWvShow.setBackgroundColor(Color.rgb(255, 255, 255));
        initWebView(pg);
        initWebChromeClient();
        initWebSettings();
        initWebViewClient();
        mWvShow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWvShow.canGoBack()) { // 表示按返回键
                        // 时的操作
                        mWvShow.goBack(); // 后退
                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                else{
                    onBackPressed();
                }
                }

                return Webview.super.onKeyDown(keyCode, event);
            }
        });




    }
    private void initWebView(String url){
            mWvShow.loadUrl(url);
        mWvShow.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        blockLoadingNetworkImage = true;

        mWvShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!blockLoadingNetworkImage){
                    mWvShow.getSettings().setBlockNetworkImage(true);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (blockLoadingNetworkImage){
                    mWvShow.getSettings().setBlockNetworkImage(false);
                }
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                        || error.getPrimaryError() == SslError.SSL_EXPIRED
                        || error.getPrimaryError() == SslError.SSL_INVALID
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                } else {
                    handler.cancel();
                }
                super.onReceivedSslError(view, handler, error);
            }


        });
    }
    private void initWebChromeClient(){
        mWvShow.setWebChromeClient(new WebChromeClient(){
            private Bitmap mDefaultVideoPoster;//默认得视频展示图

            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
                if (mTbShow != null){
                    mTbShow.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            mTbShow.setTitle(TextUtils.isEmpty(title) ? "加载中..." : title);
                        }
                    });
                }
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                if (mDefaultVideoPoster == null){
                    mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    return mDefaultVideoPoster;
                }
                return super.getDefaultVideoPoster();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(){
        WebSettings settings = mWvShow.getSettings();
        //支持获取手势焦点
        mWvShow.requestFocusFromTouch();
        settings.setDefaultTextEncodingName("utf-8");
        mWvShow.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setJavaScriptEnabled(true);
        settings.setNeedInitialFocus(false);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);//适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);//自动加载图片
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWvShow.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//这两句话也太帅了吧,android 5.0的问题
        }
    }


    private void initWebViewClient(){
        mWvShow.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                mPbShow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                mPbShow.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }
        });
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        mWvShow.setWebChromeClient(null);
        mWvShow.setWebViewClient(null);
        mWvShow.getSettings().setJavaScriptEnabled(false);
        mWvShow.clearCache(true);
    }

}
