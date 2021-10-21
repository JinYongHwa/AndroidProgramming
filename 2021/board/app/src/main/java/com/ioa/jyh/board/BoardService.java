package com.ioa.jyh.board;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoardService {

    @GET("board/mobile/list_proc")
    Call<Response> list(@Query("page") int page);

    @POST("board/mobile/userinfo")
    Call<Response> userinfo();

    @POST("board/mobile/login_proc")
    Call<Response> login(@Body User user);

    @POST("board/mobile/board/write")
    Call<Response> write(@Body Board board);

    @POST("board/mobile/view")
    Call<Response> view(@Body Board board);
}
