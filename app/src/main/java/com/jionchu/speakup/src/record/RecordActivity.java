package com.jionchu.speakup.src.record;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private int mFileIdx, mFileLength;
    public boolean isRecording = false;
    private String mFilepath;
    private MediaRecorder recorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTvStatus = findViewById(R.id.record_tv_status);
        mBtnStop = findViewById(R.id.record_btn_stop);
        mMediaPlayer = new MediaPlayer();
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

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
                // 녹음 중지
                stopRecord();

                // 원문 음성이 남은 경우
                if (mFileIdx < mFileList.size())
                    startAudio(mFileIdx);
                else { // 과제 수행이 끝난 경우
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

    // 과제 파일 전송 실패
    @Override
    public void postFileFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    // 원문 음성 재생
    private void startAudio(int index) {
        mTvStatus.setText(getString(R.string.record_audio_playing));
        mBtnStop.setVisibility(View.INVISIBLE);
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mFileList.get(index));
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
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
            mFileLength = mMediaPlayer.getDuration();
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

        try {
            recorder.prepare();
            recorder.start();

            mTvStatus.setText(getString(R.string.record_audio_recording));
            mBtnStop.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            e.printStackTrace();
        }
    }

    // 녹음 중지
    private void stopRecord() {
        isRecording = false;
        // 녹음 파일 저장
        mBtnStop.setVisibility(View.INVISIBLE);
        mTvStatus.setText(getString(R.string.record_audio_complete));

        recorder.stop();
        recorder.release();
        recorder = null;
    }
}