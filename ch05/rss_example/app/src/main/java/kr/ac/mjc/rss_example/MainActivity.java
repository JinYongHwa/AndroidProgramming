package kr.ac.mjc.rss_example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends Activity implements Callback {
    XmlParserCreator parserCreator = new XmlParserCreator() {
        @Override
        public XmlPullParser createParser() {
            try {
                return XmlPullParserFactory.newInstance().newPullParser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };
    ListView mListView;
    ArrayList<Item> newsList=new ArrayList<Item>();
    Handler handler=new Handler();
    NewsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mListView=findViewById(R.id.listview);
        mAdapter=new NewsAdapter(this,newsList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item=mAdapter.getItem(position);
                Intent intent=new Intent(ACTION_VIEW, Uri.parse(item.getLink()));
                startActivity(intent);
            }
        });

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER")
                .build();

        client.newCall(request).enqueue(this);


    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.code()==200){
            String xml=response.body().string();
            GsonXml gsonXml = new GsonXmlBuilder()
                    .setXmlParserCreator(parserCreator)
                    .setSameNameLists(true)
                    .create();
            Rss rss = gsonXml.fromXml(xml, Rss.class);
            newsList.clear();
            if(rss.channel.item!=null){
                newsList.addAll(rss.channel.item);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        }
    }
}
