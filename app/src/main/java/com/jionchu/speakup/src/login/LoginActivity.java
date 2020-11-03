package com.jionchu.speakup.src.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.main.MainActivity;
import com.jionchu.speakup.src.signup.SignUpActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void customOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_btn_login:
                SharedPreferences.Editor editor = ApplicationClass.sSharedPreferences.edit();
                editor.putInt("studentId", 1771014); //TODO: 로그인한 학생의 학번 저장
                editor.apply();

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_tv_signup:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}