package com.jionchu.speakup.src.course.models;

import com.google.gson.annotations.SerializedName;

public class CourseResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("result")
    private CourseResult result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public CourseResult getResult() {
        return result;
    }
}
