package com.jionchu.speakup.src.main;

import android.os.Bundle;
import android.view.View;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.main.adapters.AssignmentAdapter;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {

    private ArrayList<String> mAssignmentList;
    private AssignmentAdapter mAssignmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);

        mAssignmentList = new ArrayList<>();
        mAssignmentList.add("Assignment1");
        mAssignmentList.add("Assignment2");
        mAssignmentList.add("Assignment3");
        mAssignmentList.add("Assignment4");
        mAssignmentList.add("Assignment5");
        mAssignmentList.add("Assignment6");
        mAssignmentList.add("Assignment7");
        mAssignmentList.add("Assignment8");
        mAssignmentList.add("Assignment9");
        mAssignmentList.add("Assignment10");
        mAssignmentList.add("Assignment11");
        mAssignmentList.add("Assignment12");
        mAssignmentList.add("Assignment13");
        mAssignmentAdapter = new AssignmentAdapter(mAssignmentList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAssignmentAdapter);
    }

    public void customOnClick(View v) {
        switch (v.getId()) {

        }
    }
}