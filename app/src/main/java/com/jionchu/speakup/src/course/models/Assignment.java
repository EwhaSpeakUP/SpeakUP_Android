package com.jionchu.speakup.src.course.models;

import com.google.gson.annotations.SerializedName;

public class Assignment {
    @SerializedName("ASSIGNMENT_ID")
    private int assignmentId;
    @SerializedName("ASSIGNMENT_NAME")
    private String assignmentName;
    @SerializedName("DUE_DATE")
    private String dueDate;
    @SerializedName("SUBMIT_CHECK")
    private int submitCheck;

    public int getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getDueDate() {
        String strDueDate = dueDate.replace("T", " ");
        strDueDate = strDueDate.substring(0,16);
        return strDueDate;
    }

    public int getSubmitCheck() {
        return submitCheck;
    }
}
