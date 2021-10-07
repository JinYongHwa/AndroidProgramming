package com.ioa.jyh.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {

    private static BoardService instance;

    public static BoardService getBoardService(){
        if(instance==null){
            Gson gson= new GsonBuilder()
                    .registerTypeAdapter(Date.class,new DateDeserializer()).create();

            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://222.108.135.179:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            instance=retrofit.create(BoardService.class);
        }

        return instance;
    }

}
