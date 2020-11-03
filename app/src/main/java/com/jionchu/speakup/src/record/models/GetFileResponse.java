package com.jionchu.speakup.src.record.models;

import com.google.gson.annotations.SerializedName;

public class GetFileResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("result")
    private GetFileResult result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public GetFileResult getResult() {
        return result;
    }}
