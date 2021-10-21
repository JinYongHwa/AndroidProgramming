package com.ioa.jyh.board;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.Policy;
import java.util.Date;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {

    private static BoardService instance;

    public static BoardService getBoardService(Context context){
        if(instance==null){
            Gson gson= new GsonBuilder()
                    .registerTypeAdapter(Date.class,new DateDeserializer()).create();

            PersistentCookieStore cookieStore=new PersistentCookieStore(context);
            CookieManager cookieManager=new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

            OkHttpClient client=new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build();



            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://121.171.56.39:8080/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            instance=retrofit.create(BoardService.class);
        }

        return instance;
    }

}
