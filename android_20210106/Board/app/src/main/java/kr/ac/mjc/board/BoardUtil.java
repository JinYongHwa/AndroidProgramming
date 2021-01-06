package kr.ac.mjc.board;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Date;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardUtil {

    private static BoardUtil instance;
    private BoardService boardService;

    private BoardUtil(Context context){

        PersistentCookieStore persistentCookieStore=new PersistentCookieStore(context);
        CookieManager cookieManager=new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL);

        OkHttpClient client=new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(Date.class,new DateDeserailizer())
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.35.115:8080/mjc/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        boardService=retrofit.create(BoardService.class);
    }

    static public BoardUtil getInstance(Context context) {
        if(instance==null){
            instance=new BoardUtil(context);
        }
        return instance;
    }

    public BoardService getBoardService() {
        return boardService;
    }

    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }
}
