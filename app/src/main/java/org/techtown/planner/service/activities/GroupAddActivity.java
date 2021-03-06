package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;

import java.util.ArrayList;

public class GroupAddActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    GroupContent groupInfo;
    EditText groupName, PeopleMaxNum, Password;
    Button CreateButton;
    ArrayList<String> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);

        groupName = findViewById(R.id.GroupNameEditText);
        PeopleMaxNum = findViewById(R.id.MaxPeopleNumEditText);
        Password = findViewById(R.id.GroupPasswordEditText);
        CreateButton = findViewById(R.id.GroupCreateButton);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRoom();
            }
        });

    }

    private void CreateRoom() {
        String GN = groupName.getText().toString();
        int PMN = Integer.parseInt(PeopleMaxNum.getText().toString());
        String PW = Password.getText().toString();
        if(CheckCondition(GN, PMN, PW)) {
            userList.add(user.getUid());
            groupInfo = new GroupContent(GN, user.getUid(), PW, userList, PMN);
            db.collection("Group").document(user.getUid()).collection("My_Group").document(GN)
                    .set(groupInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // ?????????????????? ?????? ???????????? collectionGroup?????? ???????????????.
                    StartToast("????????? ?????????????????????.");
                    StartActivity(HomeActivity.class);
                }
            });
        }
    }

    private boolean CheckCondition(String Name, int MaxNum, String Pw) {
        if(Name.length() <= 2) {
            StartToast("???????????? 3?????? ???????????? ??????????????????.");
            return false;
        }
        if(MaxNum < 1 || MaxNum > 9) {
            StartToast("????????? ?????? 2????????? 9????????? ???????????????.");
            return false;
        }
        if(Pw.length() <= 3) {
            StartToast("?????? ??????????????? ?????? 4?????? ???????????? ??????????????????.");
            return false;
        }
        return true;
    }

    private void StartToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}