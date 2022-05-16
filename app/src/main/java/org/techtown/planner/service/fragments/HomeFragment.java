package org.techtown.planner.service.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.planner.R;
import org.techtown.planner.service.activities.EditActivity;
import org.techtown.planner.service.activities.HomeActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private View view;
    private TimetableView timetable;
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
        mother.setOnClickListener(onClickListener);
        add.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        load.setOnClickListener(onClickListener);

        // 시간표 객체 및 listener 초기화
        timetable = view.findViewById(R.id.timetable);

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(getContext(), EditActivity.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i,REQUEST_EDIT);
            }
        });

        // view가 아니라 inflate로 만든 새 객체를 return하면 view가 반영이 안됨.
        return view;
    }

    // floating button onClickListener 설정
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.plus_floating_button:
                    if(!isOpened) {
                        showFloatingMenu();
                    }else {
                        closeFloatingMenu();
                    }
                    break;
                case R.id.add_schedule_floating_button:
                    Intent i = new Intent(getActivity(),EditActivity.class);
                    i.putExtra("mode",REQUEST_ADD);
                    startActivityForResult(i,REQUEST_ADD);
                    break;
                case R.id.save_floating_button:
                    saveByPreference(timetable.createSaveData());
                    break;
                case R.id.load_floating_button:
                    loadSavedData();
                    break;
            }
        }
    };

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

    // 저장 및 불러오기 함수
    private void saveByPreference(String data){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo",data);
        editor.commit();
    }
    /** get json data from SharedPreferences and then restore the timetable */
    private void loadSavedData(){
        timetable.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String savedData = mPref.getString("timetable_demo","");
        if(savedData == null && savedData.equals("")) return;
        timetable.load(savedData);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == EditActivity.RESULT_OK_ADD) {
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if (resultCode == EditActivity.RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                }
                /** Edit -> Delete */
                else if (resultCode == EditActivity.RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
    }

}