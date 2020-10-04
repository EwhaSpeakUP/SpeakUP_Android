package com.jionchu.speakup.src.course.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourseResult {
    @SerializedName("hw_ids")
    private ArrayList<Assignment> assignmentList;

    public ArrayList<Assignment> getAssignmentList() {
        return assignmentList;
    }
}
