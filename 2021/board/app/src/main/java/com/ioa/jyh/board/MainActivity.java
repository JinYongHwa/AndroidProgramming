package com.ioa.jyh.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Common.getBoardService().list(1).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<Board> boardList=response.body().getBoardList();
                for(Board board:boardList){
                    Log.d("MainActivity",String.format("id[%s]",board.getCreateDate()));
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("MainActivity",t.toString());
            }
        });

    }
}