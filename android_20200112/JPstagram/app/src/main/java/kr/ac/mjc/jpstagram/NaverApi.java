package kr.ac.mjc.jpstagram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverApi {

    private NaverApi instance;

    private NaverApi(){
        OkHttpClient client=new OkHttpClient.Builder().build();
        Gson gson=new GsonBuilder().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

    public NaverApi getInstance() {
        if(instance==null){
            instance=new NaverApi();
        }
        return instance;
    }
}
