package com.jionchu.speakup.src.course.interfaces;

import com.jionchu.speakup.src.course.models.CourseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseRetrofitInterface {
    @GET("/assignList/{courseId}")
    Call<CourseResponse> getCourse(@Path("courseId") int courseId);
}
