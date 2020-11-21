package com.jionchu.speakup.src.result;

import com.jionchu.speakup.src.result.interfaces.ResultActivityView;
import com.jionchu.speakup.src.result.interfaces.ResultRetrofitInterface;
import com.jionchu.speakup.src.result.models.ResultResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class ResultService {
    private final ResultActivityView mResultActivityView;

    public ResultService(final ResultActivityView resultActivityView) {
        this.mResultActivityView = resultActivityView;
    }

    public void getResult(int assignmentId) {
        final ResultRetrofitInterface resultRetrofitInterface = getRetrofit().create(ResultRetrofitInterface.class);

        resultRetrofitInterface.getResult(assignmentId).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(@NotNull Call<ResultResponse> call, @NotNull Response<ResultResponse> response) {
                final ResultResponse resultResponse = response.body();
                if (resultResponse == null) {
                    mResultActivityView.getResultFailure(null);
                } else if (resultResponse.getCode() == 100){
                    mResultActivityView.getResultSuccess(resultResponse.getMessage(), resultResponse.getResult());
                } else {
                    mResultActivityView.getResultFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResultResponse> call, @NotNull Throwable t) {
                mResultActivityView.getResultFailure(null);
            }
        });
    }
}
