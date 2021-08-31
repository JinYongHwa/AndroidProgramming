package kr.ac.mjc.twich_example;

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
    ArrayList<Top> mGameList=new ArrayList<>();
    GameAdapter mGameAdapter;

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
        GameTopList gameTopList=new Gson().fromJson(body,GameTopList.class);
        mGameList.clear();
        mGameList.addAll(gameTopList.getTop());

        handler.post(new Runnable() {
            @Override
            public void run() {
                mGameAdapter.notifyDataSetChanged();
            }
        });
    }
}
