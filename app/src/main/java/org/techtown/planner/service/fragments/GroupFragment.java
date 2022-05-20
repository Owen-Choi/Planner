package org.techtown.planner.service.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.techtown.planner.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupFragment extends Fragment {

//    private GroupContent g1, g2;
//    ArrayList<GroupContent> group = new ArrayList<GroupContent>(Arrays.asList(g1, g2));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_group , container, false);

//        g1.setName("G1");
//        g2.setName("g2");
        String[] menuItems = {"number one", "number two", "number three", "number four"};
        //일단 이 이름 뜨게하기
        //그룹으로 바꿔서 리스트 띄우기

        ListView listView= (ListView) rootView.findViewById(R.id.list);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,menuItems);
        //세번째 para에 데이터 정의

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StartActivity(EachGroupActivity.class,i);
            }

            private void StartActivity(Class<EachGroupActivity> eachGroupFragmentClass, int i) {
                Intent intent= new Intent(getContext(),eachGroupFragmentClass);
                intent.putExtra("i",i+1);
                intent.putExtra("menu",menuItems[i]);
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