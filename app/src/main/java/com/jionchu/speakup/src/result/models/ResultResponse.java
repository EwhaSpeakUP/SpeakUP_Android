package com.jionchu.speakup.src.result.models;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("result")
    private ResultResult result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public ResultResult getResult() {
        return result;
    }
}
