package com.jionchu.speakup.src.login.interfaces;

import com.jionchu.speakup.src.login.models.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("/login")
    Call<LoginResponse> postLogin(@Body HashMap<String, Object> hashMap);
}
