package com.ioa.jyh.board;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoardService {

    @GET("board/mobile/list_proc")
    Call<Response> list(@Query("page") int page);
}
