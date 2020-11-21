package com.jionchu.speakup.src.signup.interfaces;

import com.jionchu.speakup.src.signup.models.SignupResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupRetrofitInterface {
    @POST("/user")
    Call<SignupResponse> postUser(@Body HashMap<String, Object> hashMap);
}
