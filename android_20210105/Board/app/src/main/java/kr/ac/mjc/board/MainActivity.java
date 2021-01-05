package kr.ac.mjc.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client=new OkHttpClient.Builder().build();
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(Date.class,new DateDeserailizer())
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.35.115:8080/mjc/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoardService boardService=retrofit.create(BoardService.class);

        Call<Result> result=boardService.list(1);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("retrofit",response.toString());

                Result result=response.body();
                for(Board board:result.getList()){
                    Log.d("retrofit",board.getTitle());
                }
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