package com.jionchu.speakup.src.record;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.record.interfaces.RecordActivityView;
import com.jionchu.speakup.src.record.models.GetFileResult;
import com.jionchu.speakup.src.result.ResultActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class RecordActivity extends BaseActivity implements RecordActivityView {

    private static final String LOG_TAG = "RecordActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private ArrayList<String> mFileList;
    private TextView mTvStatus;
    private Button mBtnStop;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean permissionToRecordAccepted = false;
    private MediaPlayer mMediaPlayer;
    private int mFileIdx;
    private String mFilepath;
    private MediaRecorder recorder = null;
    private ProgressBar mProgressBar;
    private long mFileLength;
    private int progressSeconds, progressMinutes, minutes, seconds;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTvStatus = findViewById(R.id.record_tv_status);
        mBtnStop = findViewById(R.id.record_btn_stop);
        mProgressBar = findViewById(R.id.record_progress_bar);
        mMediaPlayer = new MediaPlayer();
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);

        // 원문 음성 파일 조회
        tryGetFile(ApplicationClass.sSharedPreferences.getInt("assignmentId", 0));
        mFilepath = getExternalCacheDir().getAbsolutePath() +"/record";
    }

    @Override
    public void onStop() {
        super.onStop();
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
            case R.id.record_iv_back:
                finish();
                break;
            case R.id.record_btn_stop:
                progressMinutes = minutes;
                progressSeconds = seconds;
                break;
        }
    }

    // 원문 음성 파일 조회하기
    private void tryGetFile(int assignmentId) {
        final RecordService recordService = new RecordService(this);
        recordService.getFile(assignmentId);
    }

    // 과제 파일 전송하기
    private void tryPostFile(int assignmentId, ArrayList<String> encodedFileList) {
        showProgressDialog();
        final RecordService recordService = new RecordService(this);
        recordService.postFile(assignmentId, encodedFileList);
    }

    // 원문 음성 파일 조회 성공
    @Override
    public void getFileSuccess(String message, GetFileResult result) {
        mFileList = result.getFilePathList();
        startAudio(mFileIdx);
    }

    // 원문 음성 파일 조회 실패
    @Override
    public void getFileFailure(String message) {
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    // 과제 파일 전송 성공
    @Override
    public void postFileSuccess(String message) {
        hideProgressDialog();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void showProgressDialog() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mProgressDialog.show();
            }
        });
    }

    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
    }

    // 과제 파일 전송 실패
    @Override
    public void postFileFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    // 원문 음성 재생
    private void startAudio(int index) {
        handler.post(new Runnable() {
            public void run() {
                mTvStatus.setText(getString(R.string.record_audio_playing));
            }
        });
        mBtnStop.setVisibility(View.INVISIBLE);
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mFileList.get(index));
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    mFileLength = mMediaPlayer.getDuration();
                    minutes = (int) (mFileLength / 1000 / 60);
                    seconds = (int) ((mFileLength / 1000) % (60));
                    mProgressBar.setMax(minutes*60+seconds);
                    progressMinutes = 0;
                    progressSeconds = -1;
                    mProgressBar.setProgress(0);
                }
            });
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mFileIdx++;
                    String fileName = mFilepath + mFileIdx +".3gp";
                    startRecord(fileName);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 녹음 시작
    private void startRecord(String fileName) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && (progressMinutes * 60 + progressSeconds) < (minutes * 60 + seconds)) {
                    progressSeconds += 1;
                    if (progressSeconds == 60) {
                        progressMinutes++;
                        progressSeconds = 0;
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            mProgressBar.setProgress(progressMinutes * 60 + progressSeconds);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopRecord();
            }
        });

        try {
            recorder.prepare();
            recorder.start();

            mProgressBar.setVisibility(View.VISIBLE);
            thread.start();

            handler.post(new Runnable() {
                public void run() {
                    mTvStatus.setText(getString(R.string.record_audio_recording));
                }
            });
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
        mProgressBar.setVisibility(View.INVISIBLE);

        recorder.stop();
        recorder.release();
        recorder = null;

        // 원문 음성이 남은 경우
        if (mFileIdx < mFileList.size())
            startAudio(mFileIdx);
        else { // 과제 수행이 끝난 경우
            handler.post(new Runnable() {
                public void run() {
                    mTvStatus.setText(getString(R.string.record_audio_complete));
                }
            });
            ArrayList<String> encodedFileList = new ArrayList<>();
            for (int i=1;i<=mFileList.size();i++) {
                File file = new File(mFilepath+i+".3gp");
                try {
                    byte[] bytes = FileUtils.readFileToByteArray(file);
                    String encoded = Base64.encodeToString(bytes, 0);
                    Log.d("encoded"," string: "+encoded);
                    encodedFileList.add(encoded);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tryPostFile(ApplicationClass.sSharedPreferences.getInt("assignmentId", 0), encodedFileList);
        }
    }
}