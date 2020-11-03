package com.jionchu.speakup.src.main.interfaces;

import com.jionchu.speakup.src.main.models.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainRetrofitInterface {
    @GET("/index/courseList/{studentId}")
    Call<MainResponse> getCourse(@Path("studentId") int studentId);
}
