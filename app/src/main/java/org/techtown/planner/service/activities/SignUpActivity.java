package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.AppConfig;
import org.techtown.planner.R;
import org.techtown.planner.domain.member.Member;
import org.techtown.planner.domain.member.MemberService;

public class SignUpActivity extends AppCompatActivity {
//    AppConfig appConfig;
//    MemberService memberService;
    EditText Email, Password, Nickname;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        appConfig = (AppConfig) getIntent().getSerializableExtra("appConfig");
//        memberService = appConfig.memberService();
        findViewById(R.id.SignUpButton).setOnClickListener(SignUpButtonClickListener);
        Email = findViewById(R.id.SignUpEmailEditText);
        Password = findViewById(R.id.SignUpPasswordEditText);
        Nickname = findViewById(R.id.SignUpNicknameEditText);
    }

    private boolean conditionChecker(String EmailValue, String PasswordValue, String NicknameValue) {
        // 회원가입 조건들. 추가 예정
        if (EmailValue.length() != 0 && PasswordValue.length() != 0 && NicknameValue.length() != 0)
            return true;
        else
            return false;
    }


    private void SignUp(Member member) {
        firebaseAuth.createUserWithEmailAndPassword(member.getEmail(), member.getPw()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.e("SignUp", "계정생성 성공");
                    // 생성한 계정의 firebaseAuth 바로 가져오기. uid를 얻기 위함.
                    user = firebaseAuth.getCurrentUser();
                    // 계정 생성에 성공할 경우만 DB에 정보를 저장.
                    firebaseFirestore.collection("user").document(user.getUid()).set(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Log.e("SignUp", "회원가입 완료");
                            } else{
                                Log.e("SignUp", "회원가입 실패");
                            }
                        }
                    });
                } else {
                    Log.e("SignUp", "계정생성 실패");
                }
            }
        });
    }

    View.OnClickListener SignUpButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String EmailValue, PasswordValue, NicknameValue;
            EmailValue = Email.getText().toString();
            PasswordValue = Password.getText().toString();
            NicknameValue = Nickname.getText().toString();
            if (conditionChecker(EmailValue, PasswordValue, NicknameValue)) {
                Member member = new Member(EmailValue, PasswordValue, NicknameValue);
                SignUp(member);
//                if(memberService.SignUp(member)) {
//                    Log.e("SignUpActivity", "회원가입 완료");
//                } else {
//                    Log.e("SignUpActivity", "회원가입 실패");
//                }
//            } else {
//                Log.e("SignUpActivity", "회원가입 조건 불충분");
//            }
            }
        }
    };
}