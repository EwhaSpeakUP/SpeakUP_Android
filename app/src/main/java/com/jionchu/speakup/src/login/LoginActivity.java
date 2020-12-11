package com.jionchu.speakup.src.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.login.interfaces.LoginActivityView;
import com.jionchu.speakup.src.login.models.LoginResult;
import com.jionchu.speakup.src.main.MainActivity;
import com.jionchu.speakup.src.signup.SignUpActivity;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    private EditText mEtId, mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtId = findViewById(R.id.login_et_id);
        mEtPwd = findViewById(R.id.login_et_pwd);
    }

    public void customOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_btn_login:
                String id = mEtId.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                if (id.isEmpty())
                    showCustomToast(getString(R.string.login_id_hint));
                else if (pwd.isEmpty())
                    showCustomToast(getString(R.string.login_pwd_hint));
                else
                    tryPostLogin(id, pwd);
                break;
            case R.id.login_tv_signup:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void tryPostLogin(String id, String pwd) {
        LoginService loginService = new LoginService(this);
        loginService.postLogin(id, pwd);
        showProgressDialog();
    }

    // 로그인 성공
    @Override
    public void loginSuccess(String message, LoginResult result) {
        hideProgressDialog();

        SharedPreferences.Editor editor = ApplicationClass.sSharedPreferences.edit();
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, result.getJwt());
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // 로그인 실패
    @Override
    public void loginFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}