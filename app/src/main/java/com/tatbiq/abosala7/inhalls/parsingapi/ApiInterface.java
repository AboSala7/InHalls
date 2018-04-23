package com.tatbiq.abosala7.inhalls.parsingapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("api/hall/getallhalls")
    Call<String> getAllHalls();
}
