package kr.ac.mjc.board;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {

    @POST("mobile/list.do")
    public Call<Result> list(@Query("page") int page);

    @POST("mobile/board.do")
    public Call<Result> item(@Query("id") int id);
}
