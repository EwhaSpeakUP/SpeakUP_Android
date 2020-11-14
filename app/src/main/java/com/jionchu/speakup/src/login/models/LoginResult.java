package com.jionchu.speakup.src.login.models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("access_token")
    private String jwt;

    public String getJwt() {
        return jwt;
    }
}
