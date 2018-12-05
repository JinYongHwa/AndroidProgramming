## build.gradle

``` gradle
apply plugin: 'com.android.application'

android {
    compileSdkVersion 28
    defaultConfig {
        applicationId "kr.ac.mjc.twich_exmaple"
        minSdkVersion 15
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation 'com.google.code.gson:gson:2.2.4'
    implementation 'com.github.bumptech.glide:glide:4.8.0'
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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callback{

    GameAdapter mGameAdapter;
    ArrayList<Top> mGameList=new ArrayList<Top>();
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.listview);
        mGameAdapter=new GameAdapter(this,mGameList);
        listView.setAdapter(mGameAdapter);

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://api.twitch.tv/kraken/games/top")
                .header("Client-ID","x5it7a7lxy7peym0vrpossak24beni")
                .header("Accept","application/vnd.twitchtv.v5+json")
                .build();
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String body=response.body().string();
        TopGameList topGameList=new Gson().fromJson(body,TopGameList.class);
        Log.d("MainActivity",String.format("list length : %d",topGameList.getTop().size()));
        mGameList.clear();
        mGameList.addAll( topGameList.getTop() );

        handler.post(new Runnable() {
            @Override
            public void run() {
                mGameAdapter.notifyDataSetChanged();
            }
        });
    }
}
```

## TopGameList
``` java
import java.util.ArrayList;

public class TopGameList {
    private int _total;
    private ArrayList<Top> top;

    public int get_total() {
        return _total;
    }

    public void set_total(int _total) {
        this._total = _total;
    }

    public ArrayList<Top> getTop() {
        return top;
    }

    public void setTop(ArrayList<Top> top) {
        this.top = top;
    }
}
```

## Top.java
``` java

public class Top {
    private int channels;
    private int viewers;
    private Game game;

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
```

## Top.java
``` java

public class Game {
    private int _id;
    private int giantbomb_id;
    private String name;
    private int popularity;
    private Image box;
    private Image logo;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGiantbomb_id() {
        return giantbomb_id;
    }

    public void setGiantbomb_id(int giantbomb_id) {
        this.giantbomb_id = giantbomb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Image getBox() {
        return box;
    }

    public void setBox(Image box) {
        this.box = box;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }
}
```

## item_game.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <ImageView
        android:id="@+id/image_iv"
        android:layout_width="60dp"
        android:layout_height="60dp"
        app:srcCompat="@mipmap/ic_launcher" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:orientation="vertical">

        <TextView
            android:id="@+id/title_tv"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:text="TextView"
            android:textSize="23sp" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/channel_tv"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="bottom"
                android:text="TextView" />

            <TextView
                android:id="@+id/viewer_tv"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="bottom"
                android:text="TextView" />
        </LinearLayout>
    </LinearLayout>
</LinearLayout>
```

## GameItemLayout
``` java
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GameItemLayout extends LinearLayout {
    ImageView imageIv;
    TextView titleTv;
    TextView channelTv;
    TextView viewerTv;


    public GameItemLayout(Context context) {
        super(context);
        LayoutInflater inflater=
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_game,this,true);
        imageIv=findViewById(R.id.image_iv);
        titleTv=findViewById(R.id.title_tv);
        channelTv=findViewById(R.id.channel_tv);
        viewerTv=findViewById(R.id.viewer_tv);
    }
    public void setTop(Top top){
        titleTv.setText(top.getGame().getName());
        channelTv.setText(String.format("채널 %d",top.getChannels()));
        viewerTv.setText(String.format("시청자 %d",top.getViewers()));
        Glide.with(imageIv).load(top.getGame().getLogo().getLarge()).into(imageIv);
    }
}
```

## GameAdapter.java
``` java

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GameAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Top> mGameList;

    public GameAdapter(Context context, ArrayList<Top> gameList){
        this.mContext=context;
        this.mGameList=gameList;
    }

    @Override
    public int getCount() {
        return mGameList.size();
    }

    @Override
    public Top getItem(int position) {
        return mGameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameItemLayout itemLayout;
        if(convertView==null){
            itemLayout=new GameItemLayout(mContext);
        }
        else{
            itemLayout= (GameItemLayout) convertView;
        }
        Top top=getItem(position);
        itemLayout.setTop(top);

        return itemLayout;
    }
}
```

## AndroidManifest.xml
``` xml
<uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

