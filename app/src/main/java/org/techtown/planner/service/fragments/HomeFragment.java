package org.techtown.planner.service.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.planner.R;

public class HomeFragment extends Fragment {

    private View view;
    // Floating button 관련 변수
    FloatingActionButton add, save, load;
    boolean isOpened = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        // floating button들 초기화 및 클릭 이벤트 설정
        FloatingActionButton mother = (FloatingActionButton)view.findViewById(R.id.plus_floating_button);
        add = (FloatingActionButton)view.findViewById(R.id.add_schedule_floating_button);
        save = (FloatingActionButton)view.findViewById(R.id.save_floating_button);
        load = (FloatingActionButton)view.findViewById(R.id.load_floating_button);

        mother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("temp", "onClick: ");
                if(!isOpened) {
                    showFloatingMenu();
                }else {
                    closeFloatingMenu();
                }
            }
        });
        // view가 아니라 inflate로 만든 새 객체를 return하면 view가 반영이 안됨.
        return view;
    }
    // floating button 애니메이션이랑 하위메뉴 담당하는 함수
    private void showFloatingMenu(){
        isOpened = true;
        add.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        save.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        load.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }
    private void closeFloatingMenu(){
        isOpened = false;
        add.animate().translationY(0);
        save.animate().translationY(0);
        load.animate().translationY(0);
    }

}