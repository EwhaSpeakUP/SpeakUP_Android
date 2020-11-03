package com.jionchu.speakup.src.main.models;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("COURSE_ID")
    private int courseId;
    @SerializedName("PROFESSOR")
    private int professorId;
    @SerializedName("SUBJECT_ID")
    private int subjectId;
    @SerializedName("SUBJECT_NAME")
    private String subjectName;
    @SerializedName("COURSE_GROUP")
    private int courseGroup;

    public int getCourseId() {
        return courseId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCourseGroup() {
        return courseGroup;
    }
}
