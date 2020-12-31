package kr.ac.mjc.rssreader;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView title_tv=findViewById(R.id.title_tv);
        WebView webview=findViewById(R.id.webview);


        Item item= (Item) getIntent().getSerializableExtra("item");
        title_tv.setText(item.getTitle());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl(item.getLink());
    }
}
