package com.jionchu.speakup.src.record.interfaces;

import com.jionchu.speakup.src.record.models.GetFileResponse;
import com.jionchu.speakup.src.record.models.PostFileResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecordRetrofitInterface {
    @GET("/assign/{assignId}")
    Call<GetFileResponse> getFile(@Path("assignId") int assignId);

    @POST("/assign/{assignId}")
    Call<PostFileResponse> postFile(@Path("assignId") int assignId,
                                    @Body HashMap<String, Object> hashMap);
}
