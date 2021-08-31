package kr.ac.mjc.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardUtil {

    private static BoardUtil instance;
    private BoardService boardService;

    private BoardUtil(){
        OkHttpClient client=new OkHttpClient.Builder().build();
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

    static public BoardUtil getInstance() {
        if(instance==null){
            instance=new BoardUtil();
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
