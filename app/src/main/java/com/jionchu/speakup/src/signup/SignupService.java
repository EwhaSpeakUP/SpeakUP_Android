package com.jionchu.speakup.src.signup;

import com.jionchu.speakup.src.signup.interfaces.SignupActivityView;
import com.jionchu.speakup.src.signup.interfaces.SignupRetrofitInterface;
import com.jionchu.speakup.src.signup.models.SignupResponse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class SignupService {
    private final SignupActivityView mSignupActivityView;

    public SignupService(final SignupActivityView signupActivityView) {
        this.mSignupActivityView = signupActivityView;
    }

    public void postUser(String studentId, String id, String pwd) {
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("st_id", studentId);
        hashMap.put("id", id);
        hashMap.put("password", pwd);
        signupRetrofitInterface.postUser(hashMap).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignupResponse> call, @NotNull Response<SignupResponse> response) {
                final SignupResponse signupResponse = response.body();
                if (signupResponse == null) {
                    mSignupActivityView.signupFailure(null);
                } else if (signupResponse.getCode() == 100){
                    mSignupActivityView.signupSuccess(signupResponse.getMessage());
                } else {
                    mSignupActivityView.signupFailure(signupResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SignupResponse> call, @NotNull Throwable t) {
                mSignupActivityView.signupFailure(null);
            }
        });
    }
}
