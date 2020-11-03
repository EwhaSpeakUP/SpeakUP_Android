package com.jionchu.speakup.src.record;

import com.jionchu.speakup.src.record.interfaces.RecordActivityView;
import com.jionchu.speakup.src.record.interfaces.RecordRetrofitInterface;
import com.jionchu.speakup.src.record.models.GetFileResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class RecordService {
    private final RecordActivityView mRecordActivityView;

    public RecordService(final RecordActivityView recordActivityView) {
        this.mRecordActivityView = recordActivityView;
    }

    public void getFile(int assignmentId) {
        final RecordRetrofitInterface recordRetrofitInterface = getRetrofit().create(RecordRetrofitInterface.class);

        recordRetrofitInterface.getFile(assignmentId).enqueue(new Callback<GetFileResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetFileResponse> call, @NotNull Response<GetFileResponse> response) {
                final GetFileResponse courseResponse = response.body();
                if (courseResponse == null) {
                    mRecordActivityView.getFileFailure(null);
                } else if (courseResponse.getCode() == 100){
                    mRecordActivityView.getFileSuccess(courseResponse.getMessage(), courseResponse.getResult());
                } else {
                    mRecordActivityView.getFileFailure(courseResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetFileResponse> call, @NotNull Throwable t) {
                mRecordActivityView.getFileFailure(null);
            }
        });
    }
}
