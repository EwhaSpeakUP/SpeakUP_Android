package com.jionchu.speakup.src.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.main.adapters.CourseAdapter;
import com.jionchu.speakup.src.main.interfaces.MainActivityView;
import com.jionchu.speakup.src.main.models.Course;
import com.jionchu.speakup.src.main.models.MainResult;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements MainActivityView {

    private ArrayList<Course> mCourseList;
    private CourseAdapter mCourseAdapter;
    private ConstraintLayout mClProgressBar;
    private TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        mClProgressBar = findViewById(R.id.main_cl_progressbar);
        mTvEmpty = findViewById(R.id.main_tv_empty);

        mCourseList = new ArrayList<>();
        mCourseAdapter = new CourseAdapter(mCourseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mCourseAdapter);

        // 강의 목록 조회
        tryGetCourse(ApplicationClass.sSharedPreferences.getInt("studentId", 0));
    }

    public void customOnClick(View v) {
        switch (v.getId()) {

        }
    }

    // 강의 목록 조회하기
    private void tryGetCourse(int studentId) {
        mClProgressBar.setVisibility(View.VISIBLE);
        final MainService mainService = new MainService(this);
        mainService.getCourse(studentId);
    }

    // 강의 목록 조회 성공
    @Override
    public void getCourseSuccess(String message, MainResult result) {
        mClProgressBar.setVisibility(View.GONE);

        if (result.getClassList().size() == 0)
            mTvEmpty.setVisibility(View.VISIBLE);
        else
            mTvEmpty.setVisibility(View.INVISIBLE);

        mCourseList.addAll(result.getClassList());
        mCourseAdapter.notifyDataSetChanged();
    }

    // 강의 목록 조회 실패
    @Override
    public void getCourseFailure(String message) {
        mClProgressBar.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}