package kr.ac.mjc.jpstagram;

import kr.ac.mjc.jpstagram.model.NaverResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverService {

    @Headers({"x-naver-client-id:lwMoMZW9BnKhSYGD93L6","x-naver-client-secret:bC2wMFVmmU"})
    @GET("v1/search/shop.json")
    public Call<NaverResult> shopList(@Query("query") String query,@Query("display") int display,@Query("start")int start);
}
