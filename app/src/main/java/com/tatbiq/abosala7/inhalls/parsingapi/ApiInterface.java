package com.tatbiq.abosala7.inhalls.parsingapi;

import com.tatbiq.abosala7.inhalls.hallsearchadapter.InHallsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("api/hall/getallhalls")
    Call<List<InHallsData>> getAllHalls();

    @GET("api/hall/getallhalls")
    Call<String> getStringData();
}
