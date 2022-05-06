package org.techtown.planner.domain.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.techtown.planner.AppConfig;
import org.techtown.planner.R;

public class MainActivity extends AppCompatActivity {
    AppConfig appConfig = new AppConfig();
    MemberService memberService = appConfig.memberService();
    TextView EmailInput, PasswordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmailInput = findViewById(R.id.SignInEmailEditText);
        PasswordInput = findViewById(R.id.SignInPasswordEditText);
        findViewById(R.id.SignInButton).setOnClickListener(SignInClickListener);
    }

    //버튼 누르면 appConfig의 SignIn() 메서드 실행되게끔.
    View.OnClickListener SignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // memberService의 SignIn 메서드는 로그인에 성공하면 true, 실패하면 false를 반환한다.
            if(memberService.SignIn(EmailInput.getText().toString(), PasswordInput.getText().toString())) {
                Log.e("로그인 로그", "로그인 성공");
            } else {
                Log.e("로그인 로그", "로그인 실패");
            }
        }
    };
}