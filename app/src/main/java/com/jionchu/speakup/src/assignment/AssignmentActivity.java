package com.jionchu.speakup.src.assignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.record.RecordActivity;
import com.jionchu.speakup.src.result.ResultActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidx.annotation.RequiresApi;

public class AssignmentActivity extends BaseActivity {

    private int mSubmitStatus;
    private AlertDialog mSpeedDialog;
    private long mRemainMinutes;

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
        if (mSubmitStatus == 0) {
            tvSubmitStatus.setText(getString(R.string.assignment_submit_no));
            tvSubmitStatus.setTextColor(Color.parseColor("#ffff0000"));
        } else
            tvSubmitStatus.setText(getString(R.string.assignment_submit_yes));
        String dueDate = getIntent().getStringExtra("assignmentDueDate");
        tvDueDate.setText(dueDate);
        LocalDateTime dueDateTime = LocalDateTime.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        mRemainMinutes = Duration.between(LocalDateTime.now(), dueDateTime).toMinutes();
        int remainDays;
        int remainHours;
        String remain = "";

        if (mRemainMinutes > 0) {
            remainDays = (int)mRemainMinutes/(60*24);
            remainHours = (int)mRemainMinutes%(60*24)/60;
            mRemainMinutes = mRemainMinutes%60;

            if (remainDays != 0)
                remain += remainDays + getString(R.string.day_unit)+" ";
            if (remainHours != 0)
                remain += remainHours + getString(R.string.hour_unit)+" ";
            if (remainDays == 0 && remainHours == 0)
                remain = mRemainMinutes + getString(R.string.minute_unit);
        } else {
            remain = getString(R.string.assignment_remain_late);
            tvRemain.setTextColor(Color.parseColor("#ffff0000"));
        }

        tvRemain.setText(remain);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_speed, null, false);
        builder.setView(view);

        mSpeedDialog = builder.create();
        mSpeedDialog.setCanceledOnTouchOutside(false);

        TextView tvCancel = view.findViewById(R.id.dialog_speed_tv_cancel);
        TextView tvComplete = view.findViewById(R.id.dialog_speed_tv_complete);
        final RadioGroup rg = view.findViewById(R.id.dialog_speed_rg);

        tvCancel.setOnClickListener(v-> mSpeedDialog.dismiss());

        tvComplete.setOnClickListener(v -> {
            SharedPreferences.Editor editor = ApplicationClass.sSharedPreferences.edit();
            switch (rg.getCheckedRadioButtonId()) {
                case R.id.dialog_speed_rb1: editor.putFloat("speed", 1); break;
                case R.id.dialog_speed_rb2: editor.putFloat("speed", 1.2f); break;
                case R.id.dialog_speed_rb3: editor.putFloat("speed", 1.5f); break;
            }
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
            startActivity(intent);
            mSpeedDialog.dismiss();
        });
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
                    intent.putExtra("assignmentName", getIntent().getStringExtra("assignmentName"));
                    startActivity(intent);
                }
                break;
            case R.id.assignment_btn_record:
                if (mRemainMinutes > 0)
                    mSpeedDialog.show();
                else
                    showCustomToast(getString(R.string.assignment_remain_late));
                break;
        }
    }
}