package org.techtown.planner.domain.member;

import static java.lang.Thread.sleep;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberServiceImpl extends AppCompatActivity implements MemberService{
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    boolean SignUpReturnVal, SignInReturnVal;
    @Override
    public Boolean SignUp(Member member) {
        // firestore에 인자로 넘어온 member 저장하기
        Log.e("MemberServiceImpl", "멤버 객체 들어옴 : " + member.getEmail());
        firebaseAuth.createUserWithEmailAndPassword(member.getEmail(), member.getPw()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.e("SignUp", "계정생성 성공");
                    // 계정 생성에 성공할 경우만 DB에 정보를 저장.
                    firebaseFirestore.collection("user").document(member.getNickname()).set(member).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        return false;
    }

    @Override
    public Boolean SignIn(String Email, String Pw) {
        // 인자로 넘어온 Email이랑 Pw로 로그인하기
        return firebaseAuth.signInWithEmailAndPassword(Email, Pw).isSuccessful();
    }

    @Override
    public void delete_User() {
        // 얘는 일단 보류
    }
}
