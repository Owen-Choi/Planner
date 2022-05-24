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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.service.activities.EachGroupActivity;

import java.util.ArrayList;

public class GroupFragment extends Fragment {


    String TAG = "GroupFragment";
    private ArrayList<GroupContent> groupList;

    private FirebaseUser curUser;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GroupAdapter adapter;

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

//        adapter.addItem("group1");
//        adapter.addItem("group222");


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

            private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i) {
                Intent intent= new Intent(getContext(),eachGroupFragmentClass);
                intent.putExtra("i",i+1);
                startActivity(intent);
            }
        });

        return rootView;


    }
}

//    ArrayAdapter<GroupContent> adapter= new ArrayAdapter<GroupContent>(getActivity(), android.R.layout.simple_list_item_1, groupList)
//    {
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//            View view = super.getView(position, convertView, parent);
//            TextView tv = (TextView) view.findViewById(android.R.id.text1);
//            tv.setTextColor(Color.BLACK); //?? 리스트뷰 색깔
//            return view;
//        }
//    };
//                                                   listView.setAdapter(adapter);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), EachGroupFragment.class); //현재 화면 -> 넘어갈 화면
//                intent.putExtra("name", (Parcelable) group.get(position));
//                startActivity(intent);
//            }
//        });