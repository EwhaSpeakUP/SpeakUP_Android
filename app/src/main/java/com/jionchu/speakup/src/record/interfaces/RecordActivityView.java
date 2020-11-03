package com.jionchu.speakup.src.record.interfaces;

import com.jionchu.speakup.src.record.models.GetFileResult;

public interface RecordActivityView {
    void getFileSuccess(String message, GetFileResult result);
    void getFileFailure(String message);
}
