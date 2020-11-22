package com.jionchu.speakup.src.record;

import com.jionchu.speakup.src.record.interfaces.RecordActivityView;
import com.jionchu.speakup.src.record.interfaces.RecordRetrofitInterface;
import com.jionchu.speakup.src.record.models.GetFileResponse;
import com.jionchu.speakup.src.record.models.PostFileResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

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
                final GetFileResponse getFileResponse = response.body();
                if (getFileResponse == null) {
                    mRecordActivityView.getFileFailure(null);
                } else if (getFileResponse.getCode() == 100){
                    mRecordActivityView.getFileSuccess(getFileResponse.getMessage(), getFileResponse.getResult());
                } else {
                    mRecordActivityView.getFileFailure(getFileResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetFileResponse> call, @NotNull Throwable t) {
                mRecordActivityView.getFileFailure(null);
            }
        });
    }

    public void postFile(int assignmentId, ArrayList<String> fileList) {
        final RecordRetrofitInterface recordRetrofitInterface = getRetrofit().create(RecordRetrofitInterface.class);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("files", fileList);
        recordRetrofitInterface.postFile(assignmentId, hashMap).enqueue(new Callback<PostFileResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostFileResponse> call, @NotNull Response<PostFileResponse> response) {
                final PostFileResponse postFileResponse = response.body();
                if (postFileResponse == null) {
                    mRecordActivityView.postFileFailure(null);
                } else if (postFileResponse.getCode() == 200){
                    mRecordActivityView.postFileSuccess(postFileResponse.getMessage());
                } else {
                    mRecordActivityView.postFileFailure(postFileResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostFileResponse> call, @NotNull Throwable t) {
                mRecordActivityView.postFileFailure(null);
            }
        });
    }
}
