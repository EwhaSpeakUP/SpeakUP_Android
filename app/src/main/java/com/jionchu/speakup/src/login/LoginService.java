package com.jionchu.speakup.src.login;

import com.jionchu.speakup.src.login.interfaces.LoginActivityView;
import com.jionchu.speakup.src.login.interfaces.LoginRetrofitInterface;
import com.jionchu.speakup.src.login.models.LoginResponse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class LoginService {
    private final LoginActivityView mLoginActivityView;

    public LoginService(final LoginActivityView loginActivityView) {
        this.mLoginActivityView = loginActivityView;
    }

    public void postLogin(String id, String pwd) {
        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("password", pwd);
        loginRetrofitInterface.postLogin(hashMap).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                final LoginResponse loginResponse = response.body();
                if (loginResponse == null) {
                    mLoginActivityView.loginFailure(null);
                } else if (loginResponse.getCode() == 100){
                    mLoginActivityView.loginSuccess(loginResponse.getMessage(), loginResponse.getResult());
                } else {
                    mLoginActivityView.loginFailure(loginResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                mLoginActivityView.loginFailure(null);
            }
        });
    }
}
