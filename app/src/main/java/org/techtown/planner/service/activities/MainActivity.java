package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;

public class MainActivity extends AppCompatActivity {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
    EditText EmailInput, PasswordInput;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmailInput = findViewById(R.id.SignInEmailEditText);
        PasswordInput = findViewById(R.id.SignInPasswordEditText);
        findViewById(R.id.SignInButton).setOnClickListener(SignInClickListener);
        findViewById(R.id.MoveToSignUpButton).setOnClickListener(MoveToSignUpClickListener);
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
//        intent.putExtra("appConfig", appConfig);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean conditionChecker(String EmailValue, String PasswordValue) {
        // 로그인 조건들. 추가 예정
        if(EmailValue.length() != 0 && PasswordValue.length() != 0)
            return true;
        else
            return false;
    }

    // clickListener들
    //////////////////////////////////////////////////////////////////////////
    //버튼 누르면 appConfig의 SignIn() 메서드 실행되게끔.
    View.OnClickListener SignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.MoveToSignUpButton:
                    StartActivity(SignUpActivity.class);
                    break;
                case R.id.SignInButton:
                    SignIn();
                    break;
            }
            // memberService의 SignIn 메서드는 로그인에 성공하면 true, 실패하면 false를 반환한다.
//            String Email = EmailInput.getText().toString();
//            String Pw = PasswordInput.getText().toString();
//            if(conditionChecker(Email, Pw)) {
//                if (memberService.SignIn(Email, Pw)) {
//                    Log.e("로그인 로그", "로그인 성공");
//                } else {
//                    Log.e("로그인 로그", "로그인 실패");
//                }
//            } else {
//                Log.e("로그인 로그", "입력값 없음");
//            }
        }
    };

    private void SignIn() {
        String Email = EmailInput.getText().toString();
        String Pw = PasswordInput.getText().toString();
        if(conditionChecker(Email, Pw)) {
            firebaseAuth.signInWithEmailAndPassword(Email, Pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        StartActivity(schedule_test_activity.class);
                    } else {
                        Log.e("SignIn", "로그인 실패");
                    }
                }
            });
        }
    }

    View.OnClickListener MoveToSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StartActivity(SignUpActivity.class);
        }
    };
}