package com.jionchu.speakup.src.record.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetFileResult {
    @SerializedName("filepath")
    private ArrayList<String> filePathList;

    public ArrayList<String> getFilePathList() {
        return filePathList;
    }
}
