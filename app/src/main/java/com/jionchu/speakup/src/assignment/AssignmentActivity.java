package com.jionchu.speakup.src.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.record.RecordActivity;

public class AssignmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
    }

    public void customOnClick(View v) {
        switch (v.getId()) {
            case R.id.assignment_iv_back:
                finish();
                break;
            case R.id.assignment_btn_record:
                Intent intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}