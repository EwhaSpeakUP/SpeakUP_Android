package com.jionchu.speakup.src.course;

import com.jionchu.speakup.src.course.interfaces.CourseActivityView;
import com.jionchu.speakup.src.course.interfaces.CourseRetrofitInterface;
import com.jionchu.speakup.src.course.models.CourseResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jionchu.speakup.src.ApplicationClass.getRetrofit;

public class CourseService {
    private final CourseActivityView mCourseActivityView;

    public CourseService(final CourseActivityView courseActivityView) {
        this.mCourseActivityView = courseActivityView;
    }

    public void getAssignment(int studentId) {
        final CourseRetrofitInterface courseRetrofitInterface = getRetrofit().create(CourseRetrofitInterface.class);

        courseRetrofitInterface.getCourse(studentId).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(@NotNull Call<CourseResponse> call, @NotNull Response<CourseResponse> response) {
                final CourseResponse courseResponse = response.body();
                if (courseResponse == null) {
                    mCourseActivityView.getAssignmentFailure(null);
                } else if (courseResponse.getCode() == 100){
                    mCourseActivityView.getAssignmentSuccess(courseResponse.getMessage(), courseResponse.getResult());
                } else {
                    mCourseActivityView.getAssignmentFailure(courseResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CourseResponse> call, @NotNull Throwable t) {
                mCourseActivityView.getAssignmentFailure(null);
            }
        });
    }
}
