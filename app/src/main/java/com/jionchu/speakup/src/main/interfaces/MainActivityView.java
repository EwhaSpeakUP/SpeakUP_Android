package com.jionchu.speakup.src.main.interfaces;

import com.jionchu.speakup.src.main.models.MainResult;

public interface MainActivityView {
    void getCourseSuccess(String message, MainResult result);
    void getCourseFailure(String message);
}
