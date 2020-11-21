package com.jionchu.speakup.src.result.interfaces;

import com.jionchu.speakup.src.result.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultRetrofitInterface {
    @GET("/assign/{assignId}/result")
    Call<ResultResponse> getResult(@Path("assignId") int assignId);
}
