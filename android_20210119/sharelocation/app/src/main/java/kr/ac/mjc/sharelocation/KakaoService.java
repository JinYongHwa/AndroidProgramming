package kr.ac.mjc.sharelocation;

import kr.ac.mjc.sharelocation.model.KakaoResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface KakaoService {

    @Headers({"Authorization:KakaoAK f474bf33bf6d1e3204c83b9b23991763"})
    @GET("v2/local/geo/coord2address.json")
    public Call<KakaoResult> geocoding(@Query("y") double latitude, @Query("x") double longitude);
}
