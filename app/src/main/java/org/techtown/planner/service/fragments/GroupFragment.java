package org.techtown.planner.service.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupFragment extends Fragment {


    String TAG = "GroupFragment";
    private ArrayList<GroupContent> groupList;

    private FirebaseUser curUser;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // 수하형파트. 필요 없는거면 위 import랑 같이 지워주세요~
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;

    public GroupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_group , container, false);

        groupList = new ArrayList<>();

        //String[] menuItems = {"number one", "number two", "number three", "number four"};
        //일단 이 이름 뜨게하기
        //그룹으로 바꿔서 리스트 띄우기

        //databaseReference = database.getReference("Group");
        //databaseReference.addListenerForSingleValueEvent(new );

        ListView listView= (ListView) rootView.findViewById(R.id.list);


        db.collection("Group")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       Log.d(TAG, document.getId() + " => " + document.getData());
                                                       groupList.add(document.toObject(GroupContent.class));
                                                       System.out.println(document + "!!!!!!" +groupList);
                                                       System.out.println("for문 1 " + document.getData().get("gname"));
                                                   }
                                                   System.out.println("전체 " + groupList);
                                                   ArrayAdapter<GroupContent> adapter= new ArrayAdapter<GroupContent>(getActivity(), android.R.layout.simple_list_item_1, groupList)
                                                   {
                                                       @Override
                                                       public View getView(int position, View convertView, ViewGroup parent)
                                                       {
                                                           View view = super.getView(position, convertView, parent);
                                                           TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                                           tv.setTextColor(Color.BLACK); //?? 리스트뷰 색깔
                                                           return view;
                                                       }
                                                   };
                                                   listView.setAdapter(adapter);
                                               }
                                           }
                                       });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StartActivity(EachGroupActivity.class,i);
            }

            private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i) {
                Intent intent= new Intent(getContext(),eachGroupFragmentClass);
                intent.putExtra("i",i+1);
                //intent.putExtra("menu",menuItems[i]);
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), EachGroupFragment.class); //현재 화면 -> 넘어갈 화면
//                intent.putExtra("name", (Parcelable) group.get(position));
//                startActivity(intent);
//            }
//        });
        return rootView;

    }
}