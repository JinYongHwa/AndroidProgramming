package kr.ac.mjc.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    BoardAdapter mAdapter;
    List<Board> mBoardList=new ArrayList<>();

    Handler handler=new Handler();

    int mPage=1;
    ProgressBar mloadingPb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mloadingPb=findViewById(R.id.loading_pb);
        RecyclerView listRv=findViewById(R.id.list_rv);

        mAdapter=new BoardAdapter(this,mBoardList);
        listRv.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listRv.setLayoutManager(linearLayoutManager);

        listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
                    Toast.makeText(MainActivity.this,"scroll",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        getListPage(mPage);
    }

    public void startLoading(){
        mloadingPb.setVisibility(View.VISIBLE);
    }
    public void endLoading(){
        mloadingPb.setVisibility(GONE);
    }

    public void getListPage(int page){
        BoardService boardService=BoardUtil.getInstance().getBoardService();
        startLoading();
        Call<Result> result=boardService.list(page);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result=response.body();
                for(Board board:result.getList()){
                    mBoardList.add(board);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        endLoading();
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit",t.toString());
            }
        });
    }

}

class DateDeserailizer implements JsonDeserializer<Date>{

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Date date=new Date(json.getAsLong());
        return date;
    }
}