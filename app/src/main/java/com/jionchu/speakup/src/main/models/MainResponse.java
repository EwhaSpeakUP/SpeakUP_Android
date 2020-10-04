package com.jionchu.speakup.src.main.models;

import com.google.gson.annotations.SerializedName;

public class MainResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("result")
    private MainResult result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public MainResult getResult() {
        return result;
    }
}
