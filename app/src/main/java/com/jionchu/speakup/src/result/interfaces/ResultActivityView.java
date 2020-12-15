package com.jionchu.speakup.src.result.interfaces;

import com.jionchu.speakup.src.result.models.ResultResult;

public interface ResultActivityView {
    void getResultSuccess(String message, ResultResult result);
    void getResultPreparing();
    void getResultFailure(String message);
}
