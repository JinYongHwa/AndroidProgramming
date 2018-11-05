## item_news.xml 수정
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

## build.gradle 추가
``` gradle

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.stanfy:gson-xml-java:0.1.+'
    implementation 'com.squareup.okhttp3:okhttp:3.11.0'
    implementation 'com.github.bumptech.glide:glide:4.8.0'
}

```



## Enclosure.java 생성
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

## NewsItemLayout.java 수정
``` java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsItemLayout extends LinearLayout{

    Context mContext;
    LayoutInflater mInflater;

    ImageView imageIv;
    TextView titleTv;
    TextView descriptionTv;

    public NewsItemLayout(Context context) {
        super(context);
        this.mContext=context;
        this.mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) mInflater.inflate(R.layout.item_news,this,true);
        titleTv=rootView.findViewById(R.id.title_tv);
        descriptionTv=rootView.findViewById(R.id.description_tv);
        imageIv=rootView.findViewById(R.id.image_iv);
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

## activity_news.xml 추가
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

## NewsActivity.java 생성
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

## NewsAdapter 수정
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
