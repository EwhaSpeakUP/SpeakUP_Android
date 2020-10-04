package com.jionchu.speakup.src.main.models;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("CLASS_ID")
    private int classId;
    @SerializedName("PROFESSOR")
    private int professorId;
    @SerializedName("SUBJECT_ID")
    private int subjectId;
    @SerializedName("SUBJECT_NAME")
    private String subjectName;
    @SerializedName("CLASS_GROUP")
    private int classGroup;

    public int getClassId() {
        return classId;
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

    public int getClassGroup() {
        return classGroup;
    }
}
