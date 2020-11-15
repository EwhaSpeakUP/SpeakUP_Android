package com.jionchu.speakup.src.assignment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.record.RecordActivity;
import com.jionchu.speakup.src.result.ResultActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidx.annotation.RequiresApi;

public class AssignmentActivity extends BaseActivity {

    private int mSubmitStatus;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        TextView tvTitle = findViewById(R.id.assignment_tv_title);
        TextView tvSubmitStatus = findViewById(R.id.assignment_tv_submit);
        TextView tvDueDate = findViewById(R.id.assignment_tv_due_date);
        TextView tvRemain = findViewById(R.id.assignment_tv_remain);

        tvTitle.setText(getIntent().getStringExtra("assignmentName"));
        mSubmitStatus = getIntent().getIntExtra("assignmentSubmit", 0);
        if (mSubmitStatus == 0)
            tvSubmitStatus.setText(getString(R.string.assignment_submit_no));
        else
            tvSubmitStatus.setText(getString(R.string.assignment_submit_yes));
        String dueDate = getIntent().getStringExtra("assignmentDueDate");
        tvDueDate.setText(dueDate);
        LocalDateTime dueDateTime = LocalDateTime.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        long remainMinutes = Duration.between(LocalDateTime.now(), dueDateTime).toMinutes();
        int remainDays = (int)remainMinutes/(60*24);
        int remainHours = (int)remainMinutes%(60*24)/60;
        remainMinutes = remainMinutes%60;
        String remain = "";
        if (remainDays != 0)
            remain += remainDays + getString(R.string.day_unit)+" ";
        if (remainHours != 0)
            remain += remainHours + getString(R.string.hour_unit)+" ";
        if (remainDays == 0 && remainHours == 0)
            remain = remainMinutes + getString(R.string.minute_unit);

        tvRemain.setText(remain);
    }

    public void customOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.assignment_iv_back:
                finish();
                break;
            case R.id.assignment_tv_result:
                if (mSubmitStatus == 0)
                    showCustomToast(getString(R.string.assignment_result_no));
                else {
                    intent = new Intent(this, ResultActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.assignment_btn_record:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}