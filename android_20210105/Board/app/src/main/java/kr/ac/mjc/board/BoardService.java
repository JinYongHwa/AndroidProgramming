package kr.ac.mjc.board;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {

    @POST("mobile/list.do")
    public Call<Result> list(@Query("page") int page);
}
