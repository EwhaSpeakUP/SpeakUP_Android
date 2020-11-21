package com.jionchu.speakup.src.result.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultResult {
    @SerializedName("html")
    private ArrayList<String> html;
    @SerializedName("statistics")
    private ArrayList<Integer> fillerStatistics;
    @SerializedName("ratio")
    private ArrayList<Integer> silenceStatistics;

    public ArrayList<String> getHtml() {
        return html;
    }

    public ArrayList<Integer> getFillerStatistics() {
        return fillerStatistics;
    }

    public ArrayList<Integer> getSilenceStatistics() {
        return silenceStatistics;
    }
}
