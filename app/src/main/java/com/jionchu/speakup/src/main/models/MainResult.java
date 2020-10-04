package com.jionchu.speakup.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainResult {
    @SerializedName("classList")
    private ArrayList<Course> classList;

    public ArrayList<Course> getClassList() {
        return classList;
    }
}
