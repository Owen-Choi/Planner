package org.techtown.planner.service.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.activities.EachGroupActivity;
import org.techtown.planner.service.activities.GroupAddActivity;

import java.util.ArrayList;

public class GroupFragment extends Fragment {


    String TAG = "GroupFragment";
    private ArrayList<GroupContent> groupList;

    private FirebaseUser curUser;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GroupAdapter adapter;

    // 그룹 추가 floating button, 철웅 추가
    FloatingActionButton add;

    public GroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_group , container, false);

        groupList = new ArrayList<>();

        ListView listView= (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        adapter = new GroupAdapter();


        setHasOptionsMenu(true);

        db.collection("Group")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       Log.d(TAG, document.getId() + " => " + document.getData());
                                                       adapter.addItem((String) document.getData().get("gname"), (ArrayList) document.getData().get("usernum"));
                                                       System.out.println((String) document.getData().get("gname") + " and " + (ArrayList) document.getData().get("usernum"));
                                                       //System.out.println(document.getData().get("gname"));
                                                       //groupList.add(document.toObject(GroupContent.class));
                                                       //System.out.println(document + "!!!!!!" +groupList);
                                                       //System.out.println("for문 1 " + document.getData().get("gname"));
                                                   }
                                                   //System.out.println("전체 " + groupList);
                                               }
                                           }
                                       });
        adapter.notifyDataSetChanged();


        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //그룹 들어가기 전 pw 입력받기
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("Input Group pw");
                alert.setMessage("ex.1234");

                final EditText pw = new EditText(getContext());
                alert.setView(pw);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String g_pw = pw.getText().toString();
                        //TODO: '비밀번호가 일치한다면' 의 조건 작성
                        StartActivity(EachGroupActivity.class,i);
                    }
                });

                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();

            }

        });

        // floating button 관련, 철웅 추가
        add = (FloatingActionButton)rootView.findViewById(R.id.add_group_floating_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager();
            }
        });

        return rootView;


    }

    private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i) {
        Intent intent= new Intent(getContext(),eachGroupFragmentClass);
        intent.putExtra("i",i+1);
        startActivity(intent);
    }

    // 철웅 추가, 오버로딩?
    private void StartActivity(Class c) {
        Intent intent = new Intent(getContext(), c);
        startActivity(intent);
    }

    // 그룹 생성 관련 dialog, 철웅 추가
    private void DialogManager() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity())
                .setTitle("Timetable Clear")
                .setMessage("새 그룹을 만드시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StartActivity(GroupAddActivity.class);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}

