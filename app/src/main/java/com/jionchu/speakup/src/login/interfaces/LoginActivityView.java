package com.jionchu.speakup.src.login.interfaces;

import com.jionchu.speakup.src.login.models.LoginResult;

public interface LoginActivityView {
    void loginSuccess(String message, LoginResult result);
    void loginFailure(String message);
}
