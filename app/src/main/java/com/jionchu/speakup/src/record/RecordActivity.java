package com.jionchu.speakup.src.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class RecordActivity extends BaseActivity {

    private static final String LOG_TAG = "RecordActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private TextView mTvStatus;
    private Button mBtnStop;
    private MediaRecorder recorder = null;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean permissionToRecordAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTvStatus = findViewById(R.id.record_tv_status);
        mBtnStop = findViewById(R.id.record_btn_stop);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        startAudio();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    public void customOnClick(View v) {
        switch (v.getId()) {
            case R.id.record_btn_stop:
                stopRecord();
                break;
        }
    }

    // 원문 음성 재생
    private void startAudio() {
        mTvStatus.setText("원문 음성 재생 중");
        mBtnStop.setVisibility(View.INVISIBLE);

        // TODO: 서버에서 불러온 음성 재생
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String fileName = getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";
                startRecord(fileName);
            }
        }, 2000);
    }

    // 녹음 시작
    private void startRecord(String fileName) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();

            mTvStatus.setText("녹음 중");
            mBtnStop.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            e.printStackTrace();
        }
    }

    // 녹음 중지
    private void stopRecord() {
        // 녹음 파일 저장
        mBtnStop.setVisibility(View.INVISIBLE);
        mTvStatus.setText("녹음 완료");

        recorder.stop();
        recorder.release();
        recorder = null;
    }
}