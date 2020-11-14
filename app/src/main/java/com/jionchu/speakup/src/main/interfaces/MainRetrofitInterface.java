package com.jionchu.speakup.src.main.interfaces;

import com.jionchu.speakup.src.main.models.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainRetrofitInterface {
    @GET("/course")
    Call<MainResponse> getCourse();
}
