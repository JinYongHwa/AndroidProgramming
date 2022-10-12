package kr.co.hhsoft.test;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        WebView webview=findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("jyh","로딩완료");
                super.onPageFinished(view, url);
            }

        });
        webview.setWebChromeClient(new WebChromeClient(){});

        webview.getSettings().setJavaScriptEnabled(true);

        String url=getIntent().getStringExtra("url");
        webview.loadUrl(url);


    }
}
