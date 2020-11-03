package com.jionchu.speakup.src.record.interfaces;

import com.jionchu.speakup.src.record.models.GetFileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecordRetrofitInterface {
    @GET("/assign/{assignId}")
    Call<GetFileResponse> getFile(@Path("assignId") int assignId);
}
