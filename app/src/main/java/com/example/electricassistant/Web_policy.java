package com.example.electricassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_policy extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_policy);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);////设置WebView属性,运行执行js脚本
        webView.loadUrl("http://www.beijing.gov.cn/zhengce/zhengcefagui/201907/t20190718_101721.html");//加载url地址
        setContentView(webView);//调用Activity提供的setContentView将webView显示

    }
}
