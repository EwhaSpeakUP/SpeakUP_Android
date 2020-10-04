package com.jionchu.speakup.src.course.models;

import com.google.gson.annotations.SerializedName;

public class Assignment {
    @SerializedName("HW_ID")
    private int assignmentId;
    @SerializedName("HW_NAME")
    private String assignmentName;

    public int getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
