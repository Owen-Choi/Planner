package org.techtown.planner.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.AppConfig;
import org.techtown.planner.R;
import org.techtown.planner.domain.member.Member;
import org.techtown.planner.domain.member.MemberService;

public class SignUpActivity extends AppCompatActivity {
    AppConfig appConfig;
    MemberService memberService;
    EditText Email, Password, Nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        appConfig = (AppConfig) getIntent().getSerializableExtra("appConfig");
        memberService = appConfig.memberService();
        findViewById(R.id.SignUpButton).setOnClickListener(SignUpButtonClickListener);
        Email = findViewById(R.id.SignUpEmailEditText);
        Password = findViewById(R.id.SignUpPasswordEditText);
        Nickname = findViewById(R.id.SignUpNicknameEditText);
    }

    private boolean conditionChecker(String EmailValue, String PasswordValue, String NicknameValue) {
        // 회원가입 조건들. 추가 예정
        if(EmailValue.length() != 0 && PasswordValue.length() != 0 && NicknameValue.length() != 0)
            return true;
        else
            return false;
    }

    View.OnClickListener SignUpButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String EmailValue, PasswordValue, NicknameValue;
            EmailValue = Email.getText().toString();
            PasswordValue = Password.getText().toString();
            NicknameValue = Nickname.getText().toString();
            if(conditionChecker(EmailValue, PasswordValue, NicknameValue)) {
                Member member = new Member(EmailValue, PasswordValue, NicknameValue);
                if(memberService.SignUp(member)) {
                    Log.e("SignUpActivity", "회원가입 완료");
                } else {
                    Log.e("SignUpActivity", "회원가입 실패");
                }
            } else {
                Log.e("SignUpActivity", "회원가입 조건 불충분");
            }
        }
    };
}