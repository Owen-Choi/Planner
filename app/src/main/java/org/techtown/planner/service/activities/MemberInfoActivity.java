package org.techtown.planner.service.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.domain.member.Member;

public class MemberInfoActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    TextView Email, Nickname, Name, Birthdate, Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_member_info);
        setContentView(R.layout.designed_member_info);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(firebaseUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Member member = documentSnapshot.toObject(Member.class);
                if(member != null) {
                    Email = (TextView)findViewById(R.id.EmailInfo);
                    Nickname = (TextView)findViewById(R.id.NicknameInfo);
//                    Name = (TextView)findViewById(R.id.NameInfo);
                    Birthdate = (TextView)findViewById(R.id.BirthDateInfo);
                    Gender = (TextView)findViewById(R.id.GenderInfo);

                    Email.setText(member.getEmail());
                    Nickname.setText(member.getNickname());
//                    Name.setText(member.getRealName());
                    Birthdate.setText(member.getBirthDate());
                    Gender.setText(member.getGender());
                }
                else
                    Log.d("temp", "null member info");
            }
        });
    }



}