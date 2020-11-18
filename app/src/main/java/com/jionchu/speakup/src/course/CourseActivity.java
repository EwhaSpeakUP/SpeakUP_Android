package com.jionchu.speakup.src.course;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.course.adapters.AssignmentAdapter;
import com.jionchu.speakup.src.course.interfaces.CourseActivityView;
import com.jionchu.speakup.src.course.models.Assignment;
import com.jionchu.speakup.src.course.models.CourseResult;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseActivity extends BaseActivity implements CourseActivityView {

    private ArrayList<Assignment> mAssignmentList;
    private AssignmentAdapter mAssignmentAdapter;
    private ConstraintLayout mClProgressBar;
    private TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        RecyclerView recyclerView = findViewById(R.id.course_recyclerview);
        TextView tvTitle = findViewById(R.id.course_tv_title);
        mClProgressBar = findViewById(R.id.course_cl_progressbar);
        mTvEmpty = findViewById(R.id.course_tv_empty);

        // 강의명 설정
        String title = getIntent().getStringExtra("courseName");
        tvTitle.setText(title);

        mAssignmentList = new ArrayList<>();
        mAssignmentAdapter = new AssignmentAdapter(mAssignmentList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAssignmentAdapter);

        // 과제 목록 조회
        int courseId = getIntent().getIntExtra("courseId", 0);
        tryGetAssignment(courseId);
    }

    public void customOnClick(View v) {
        if (v.getId() == R.id.course_iv_back) {
            finish();
        }
    }

    // 과제 목록 조회하기
    private void tryGetAssignment(int courseId) {
        mClProgressBar.setVisibility(View.VISIBLE);
        final CourseService courseService = new CourseService(this);
        courseService.getAssignment(courseId);
    }

    // 과제 목록 조회 성공
    @Override
    public void getAssignmentSuccess(String message, CourseResult result) {
        mClProgressBar.setVisibility(View.GONE);

        if (result != null &&result.getAssignmentList().size() != 0) {
            mTvEmpty.setVisibility(View.INVISIBLE);
            mAssignmentList.addAll(result.getAssignmentList());
        }
        else {
            mTvEmpty.setVisibility(View.VISIBLE);
        }

        mAssignmentAdapter.notifyDataSetChanged();
    }

    // 과제 목록 조회 실패
    @Override
    public void getAssignmentFailure(String message) {
        mClProgressBar.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}