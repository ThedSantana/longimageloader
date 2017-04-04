package com.example.longimageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.sheiud.redbible.longimageloader.LongImageLoader;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_scroll_view);
        final WebView webView = (WebView) findViewById(R.id.web_view);

        LongImageLoader.getInstance().setWebViewByImageUrl(webView, "http://www.pravs.co.kr/shop/lib/meditor/../../data/editor/1467710929.jpg");
    }
}
