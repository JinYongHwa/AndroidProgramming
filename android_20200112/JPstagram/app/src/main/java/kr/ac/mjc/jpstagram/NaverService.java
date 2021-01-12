package kr.ac.mjc.jpstagram;

import kr.ac.mjc.jpstagram.model.NaverResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NaverService {

    @GET("v1/search/shop.json")
    public Call<NaverResult> shopList();
}
