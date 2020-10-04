package com.jionchu.speakup.src.main;

import com.jionchu.speakup.src.main.interfaces.MainActivityView;
import com.jionchu.speakup.src.main.interfaces.MainRetrofitInterface;
import com.jionchu.speakup.src.main.models.MainResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class MainService {
    private final MainActivityView mMainActivityView;

    public MainService(final MainActivityView mainActivityView) {
        this.mMainActivityView = mainActivityView;
    }

    public void getCourse(int studentId) {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);

        mainRetrofitInterface.getCourse(studentId).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(@NotNull Call<MainResponse> call, @NotNull Response<MainResponse> response) {
                final MainResponse mainResponse = response.body();
                if (mainResponse == null) {
                    mMainActivityView.getCourseFailure(null);
                } else if (mainResponse.getCode() == 100){
                    mMainActivityView.getCourseSuccess(mainResponse.getMessage(), mainResponse.getResult());
                } else {
                    mMainActivityView.getCourseFailure(mainResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MainResponse> call, @NotNull Throwable t) {
                mMainActivityView.getCourseFailure(null);
            }
        });
    }
}
