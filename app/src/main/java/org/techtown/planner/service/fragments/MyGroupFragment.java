package org.techtown.planner.service.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.activities.EachGroupActivity;

import java.util.ArrayList;

public class MyGroupFragment extends Fragment {

    //TODO: 현재 GroupFragment 그대로 가져온 상태. 안에 들어가는 my_group_list 를 내 그룹으로 다시 바꿔줘야함

    String TAG = "GroupFragment";
    private ArrayList<GroupContent> groupList;

    private FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GroupAdapter adapter;

    public MyGroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_my_group , container, false);

        groupList = new ArrayList<>();

        ListView listView= (ListView) rootView.findViewById(R.id.my_group_list);
        listView.setAdapter(adapter);
        adapter = new GroupAdapter();

        db.collectionGroup("My_Group").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        GroupContent tempContent = document.toObject(GroupContent.class);
                        if(MemberCheck(tempContent.getUserList())) {
                            adapter.addItem(tempContent);
                        }
                    }
                }
            }
        });
        adapter.notifyDataSetChanged();


        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StartActivity(EachGroupActivity.class,i);
            }

            private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i) {
                Intent intent= new Intent(getContext(),eachGroupFragmentClass);
                intent.putExtra("i",i+1);
                startActivity(intent);
            }
        });

        return rootView;


    }

    private boolean MemberCheck(ArrayList<String> memberUid) {
        for (String uid : memberUid) {
            if(uid.equals(curUser.getUid()))
                return true;
        }
        return false;
    }

}
