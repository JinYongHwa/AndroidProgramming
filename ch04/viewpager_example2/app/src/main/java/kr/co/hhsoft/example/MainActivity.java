package kr.co.hhsoft.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView timelineRv;
    TimelineAdapter timelineAdapter;
    ArrayList<Post> mPostList=new ArrayList<>();

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timelineRv=findViewById(R.id.list_rv);
        timelineAdapter=new TimelineAdapter(mPostList);

        timelineRv.setAdapter(timelineAdapter);

        timelineRv.setLayoutManager(new LinearLayoutManager(this));

        timelineRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    Log.d("jyh","test");
                }
            }
        });

        OkHttpClient client=new OkHttpClient();
        Request reuqest=new Request.Builder()
                .get()
                .url("https://api.thecatapi.com/v1/images/search?limit=10&page=1")
                .build();

        client.newCall(reuqest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MainActivity",e.toString());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.d("MainActivity",response.body().string());
                Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
                List<Post> postList=new Gson().fromJson(response.body().string(),listType);

                mPostList.addAll(postList);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timelineAdapter.notifyDataSetChanged();
                    }
                });


            }
        });
    }


}