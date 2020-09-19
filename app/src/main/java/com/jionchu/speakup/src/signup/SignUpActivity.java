package com.jionchu.speakup.src.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.main.MainActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void customOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.signup_iv_back:
                finish();
                break;
            case R.id.signup_btn_signup: //TODO 서버 통해 회원가입
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}