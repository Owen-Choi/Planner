package org.techtown.planner.domain.member;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class MemberServiceImpl implements MemberService {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void SignUp(Member member) {
        // firestore에 인자로 넘어온 member 저장하기
        Log.e("MemberServiceImpl", "멤버 객체 들어옴 : " + member.getEmail());
    }

    @Override
    public Boolean SignIn(String Email, String Pw) {
        // 인자로 넘어온 Email이랑 Pw로 로그인하기
        // 일단 유효성 검사부터 하고 통과하면 아래 메서드 수행
        return firebaseAuth.signInWithEmailAndPassword(Email, Pw).isSuccessful();
    }

    @Override
    public void delete_User() {
        // 얘는 일단 보류
    }
}
