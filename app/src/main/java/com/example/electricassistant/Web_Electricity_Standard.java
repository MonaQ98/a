package com.example.electricassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_Electricity_Standard extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__electricity__standard);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);////设置WebView属性,运行执行js脚本
        webView.loadUrl("http://www.beijing.gov.cn/fuwu/bmfw/jmsh/jmshshjf/shjfd/dj/t1492381.htm");//加载url地址
        setContentView(webView);//调用Activity提供的setContentView将webView显示
    }
}
