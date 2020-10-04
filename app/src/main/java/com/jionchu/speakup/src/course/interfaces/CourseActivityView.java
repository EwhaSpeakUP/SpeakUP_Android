package com.jionchu.speakup.src.course.interfaces;

import com.jionchu.speakup.src.course.models.CourseResult;

public interface CourseActivityView {
    void getAssignmentSuccess(String message, CourseResult result);
    void getAssignmentFailure(String message);
}
