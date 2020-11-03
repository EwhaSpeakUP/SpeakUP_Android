package com.jionchu.speakup.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainResult {
    @SerializedName("courseList")
    private ArrayList<Course> courseList;

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
}
