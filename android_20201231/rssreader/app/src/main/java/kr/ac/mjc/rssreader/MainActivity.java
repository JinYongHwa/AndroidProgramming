package kr.ac.mjc.rssreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    NewsAdapter mAdapter;
    List<Item> newsList=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView listRv=findViewById(R.id.list_rv);
        mAdapter=new NewsAdapter(this,newsList );
        listRv.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listRv.setLayoutManager(linearLayoutManager);

        String url="https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER";

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rssreader",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("rssreader",response.body().string());
            }
        });

    }
}