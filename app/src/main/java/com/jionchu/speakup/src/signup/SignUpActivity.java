package com.jionchu.speakup.src.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.BaseActivity;
import com.jionchu.speakup.src.signup.interfaces.SignupActivityView;

public class SignUpActivity extends BaseActivity implements SignupActivityView {

    private EditText mEtStudentId, mEtId, mEtPwd, mEtPwdCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEtStudentId = findViewById(R.id.signup_et_student_id);
        mEtId = findViewById(R.id.signup_et_id);
        mEtPwd = findViewById(R.id.signup_et_pwd);
        mEtPwdCheck = findViewById(R.id.signup_et_pwd_check);
    }

    public void customOnClick(View v) {
        switch (v.getId()) {
            case R.id.signup_iv_back:
                finish();
                break;
            case R.id.signup_btn_signup:
                String studentId = mEtStudentId.getText().toString().trim();
                String id = mEtId.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                String pwdCheck = mEtPwdCheck.getText().toString().trim();
                if (studentId.isEmpty())
                    showCustomToast(getString(R.string.signup_student_id_hint));
                else if (studentId.length() != 7)
                    showCustomToast(getString(R.string.signup_student_id_length));
                else if (id.isEmpty())
                    showCustomToast(getString(R.string.login_id_hint));
                else if (pwd.isEmpty())
                    showCustomToast(getString(R.string.login_pwd_hint));
                else if (pwdCheck.isEmpty())
                    showCustomToast(getString(R.string.signup_pwd_check_hint));
                else if (!pwd.equals(pwdCheck))
                    showCustomToast(getString(R.string.signup_pwd_check_wrong));
                else
                    tryPostUser(studentId, id, pwd);
                break;
        }
    }

    // 회원가입 하기
    private void tryPostUser(String studentId, String id, String pwd) {
        showProgressDialog();
        final SignupService signupService = new SignupService(this);
        signupService.postUser(studentId, id, pwd);
    }

    // 회원가입 성공
    @Override
    public void signupSuccess(String message) {
        hideProgressDialog();
        showCustomToast(getString(R.string.signup_complete));
        finish();
    }

    // 회원가입 실패
    @Override
    public void signupFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}