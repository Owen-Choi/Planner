package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.domain.member.Member;

public class SignUpActivity extends AppCompatActivity {


    EditText Email, Password, Nickname, Name, BirthDate;
    RadioGroup GenderGroup;
    RadioButton MaleRadioButton, FemaleRadioButton;
    static String gender = "";
    static final String TAG = "SignUp";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.SignUpButton).setOnClickListener(SignUpButtonClickListener);
        Email = findViewById(R.id.SignUpEmailEditText);
        Password = findViewById(R.id.SignUpPasswordEditText);
        Nickname = findViewById(R.id.SignUpNicknameEditText);
        Name = findViewById(R.id.SignUpNameEditText);
        BirthDate = findViewById(R.id.SignUpBirthDateEditText);

        GenderGroup = findViewById(R.id.GenderRadioGroup);
        MaleRadioButton = findViewById(R.id.MaleRadioButton);
        FemaleRadioButton = findViewById(R.id.FemaleRadioButton);

        GenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.MaleRadioButton:
                        gender = "Male";
                        Log.e(TAG, "onCheckedChanged: " + "Male");
                        break;
                    case R.id.FemaleRadioButton:
                        gender = "Female";
                        Log.e(TAG, "onCheckedChanged: " + "Female");
                        break;
                }
            }
        });
    }

    private boolean conditionChecker(String EmailValue, String PasswordValue, String NicknameValue,
                                     String NameValue, String BirthDateValue) {
        // 회원가입 조건들. 추가 예정
        if (EmailValue.length() != 0 && PasswordValue.length() != 0 && NicknameValue.length() != 0
                && NameValue.length() != 0 && BirthDateValue.length() != 0 && gender.length() != 0)
            return true;
        else
            return false;
    }


    private void SignUp(Member member, String PasswordValue) {
        firebaseAuth.createUserWithEmailAndPassword(member.getEmail(), PasswordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e("SignUp", "계정생성 성공");
                    // 생성한 계정의 firebaseAuth 바로 가져오기. uid를 얻기 위함.
                    user = firebaseAuth.getCurrentUser();
                    // 계정 생성에 성공할 경우만 DB에 정보를 저장.
                    firebaseFirestore.collection("user").document(user.getUid()).set(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.e("SignUp", "회원가입 완료");
                                StartActivity(HomeActivity.class);
                            } else {
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
            String EmailValue, PasswordValue, NicknameValue, NameValue, BirthDateValue;
            EmailValue = Email.getText().toString();
            PasswordValue = Password.getText().toString();
            NicknameValue = Nickname.getText().toString();
            NameValue = Name.getText().toString();
            BirthDateValue = BirthDate.getText().toString();
            if (conditionChecker(EmailValue, PasswordValue, NicknameValue, NameValue, BirthDateValue)) {
                Member member = new Member(EmailValue, NicknameValue, NameValue, gender, BirthDateValue);
                SignUp(member, PasswordValue);
            } else {
                Log.e("signUpError", "Fail to SignUp : check the condition");
            }
        }
    };


    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}