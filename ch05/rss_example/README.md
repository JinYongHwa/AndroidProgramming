# RSS 예제
- [gson-xml](https://github.com/stanfy/gson-xml)
- [SBS RSS](http://news.sbs.co.kr/news/rss.do)

## app/build.gradle
```
implementation 'com.stanfy:gson-xml-java:0.1.+'
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
```

## AndroidManifest.xml
``` xml
<uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

## Rss.java
``` java
public class Rss {
    Channel channel;
}
```

## Channel.java
``` java
import java.util.List;

public class Channel {
    List<Item> item;
}
```

## Item.java
``` java
public class Item {
    private String title;
    private String link;
    private String description;
    private Enclosure enclosure;

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
```

## Enclosure.java 
``` java

import com.google.gson.annotations.SerializedName;

public class Enclosure {

    @SerializedName("@url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
```

## news_item.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingBottom="10dp"
    android:paddingTop="10dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/image_iv"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:src="@mipmap/ic_launcher_round" />

        <TextView
            android:id="@+id/title_tv"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="TextView"
            android:textSize="20sp" />
    </LinearLayout>

    <TextView
        android:id="@+id/description_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ellipsize="end"
        android:lines="3"
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:text="TextView" />
</LinearLayout>
```


## NewsItemLayout.java
``` java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsItemLayout extends LinearLayout{

    Context mContext;
    LayoutInflater mInflater;

    TextView titleTv;
    TextView descriptionTv;

    public NewsItemLayout(Context context) {
        super(context);
        this.mContext=context;
        this.mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) mInflater.inflate(R.layout.item_news,this,true);
        titleTv=rootView.findViewById(R.id.title_tv);
        descriptionTv=rootView.findViewById(R.id.description_tv);
    }


    public void setItem(Item item){
        titleTv.setText(item.getTitle());
        descriptionTv.setText(item.getDescription());
        if(item.getEnclosure()!=null){

            imageIv.setVisibility(VISIBLE);
            Glide.with(imageIv).load(item.getEnclosure().getUrl()).into(imageIv);
        }
        else{
            imageIv.setVisibility(GONE);
            Glide.with(imageIv).clear(imageIv);
        }
    }

}
```

## NewsAdapter.java
``` java

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    ArrayList<Item> newsList=new ArrayList<Item>();
    Context mContext;
    public NewsAdapter(Context context,ArrayList<Item> data){
        this.mContext=context;
        this.newsList=data;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Item getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsItemLayout itemLayout;
        if(convertView==null){
            itemLayout=new NewsItemLayout(mContext);
        }
        else{
            itemLayout= (NewsItemLayout) convertView;
        }
        Item item=getItem(position);
        itemLayout.setItem(item);

        return itemLayout;
    }
}
```

## activity_news.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <WebView
        android:id="@+id/webview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```
## NewsActivity.java
``` java

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        WebView webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ListView
        android:id="@+id/listview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

## MainActivity.java
``` java
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
```



