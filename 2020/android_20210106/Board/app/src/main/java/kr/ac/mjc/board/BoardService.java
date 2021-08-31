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

    @POST("mobile/checkemail.do")
    public Call<Result> checkEmail(@Query("email") String email);

    @POST("mobile/join.do")
    public Call<Result> join(@Query("email") String email,@Query("name") String name,@Query("password") String password);

    @POST("mobile/login.do")
    public Call<Result> login(@Query("email") String email,@Query("password") String password);

    @POST("mobile/userinfo.do")
    public Call<Result> userinfo();

    @POST("mobile/write.do")
    public Call<Result> write(@Query("title")String title,@Query("text") String text);

    @POST("mobile/remove.do")
    public Call<Result> remove(@Query("id") int id);

    @POST("mobile/modify.do")
    public Call<Result> modify(@Body Board board);


}
