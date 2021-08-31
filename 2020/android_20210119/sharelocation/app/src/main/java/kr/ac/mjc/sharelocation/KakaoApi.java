package kr.ac.mjc.sharelocation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoApi {

    static private KakaoApi instance;

    private KakaoService kakaoService;

    private KakaoApi(){
        OkHttpClient client=new OkHttpClient.Builder().build();
        Gson gson=new GsonBuilder().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        kakaoService=retrofit.create(KakaoService.class);
    }

    public KakaoService getKakaoService(){
        return kakaoService;
    }

    public static KakaoApi getInstance(){
        if(instance==null){
            instance=new KakaoApi();
        }
        return instance;
    }

}
