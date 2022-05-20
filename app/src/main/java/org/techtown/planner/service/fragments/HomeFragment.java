package org.techtown.planner.service.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.schedule.ScheduleInfo;
import org.techtown.planner.service.activities.EditActivity;
import org.techtown.planner.service.activities.HomeActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private static final String TAG = "HomeFragment";
    private View view;
    private TimetableView timetable;
    // Floating button 관련 변수
    FloatingActionButton add, save, load;
    boolean isOpened = false;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        // 재부팅이나 화면 전환시 시간표가 날아가는 현상을 막기 위해
        // view가 생성될때 파이어베이스에 있는 시간 정보를 다 불러와서 넣어준다.
        showSchedule();
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
        EnableFloatingButtons();
        add.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        save.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        load.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }
    private void closeFloatingMenu(){
        isOpened = false;
        add.animate().translationY(0);
        save.animate().translationY(0);
        // 뒤에 가려져 있어야 할 floating button이 앞으로 튀어나와서 메인 버튼을 가린다.
        // 그래서 load 버튼까지 애니메이션이 끝나면 서브 버튼들을 disabled로 바꿀려고 한다.
        // load 버튼 자체에 애니메이션 리스너가 붙기 때문에 버튼을 닫았을 때인 isOpened = false인 경우에만
        // 서브버튼들을 비활성화 시켜주기로 한다.
        load.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!isOpened)
                    DisableFloatingButtons();
            }
        });
    }
    private void DisableFloatingButtons() {
        add.setEnabled(false);
        save.setEnabled(false);
        load.setEnabled(false);
    }
    private void EnableFloatingButtons() {
        add.setEnabled(true);
        save.setEnabled(true);
        load.setEnabled(true);
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
                    // 파베에서 시간표 리스트를 받아오면 이런 형식으로 구성해서 띄워줄 수 있을 것 같다.
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

    private void showSchedule() {
        db.collection("Schedule").document(user.getUid()).collection("Personal_schedules")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Schedule newSchedule = getStructed(document);
                        ArrayList<Schedule> itemList = new ArrayList<>();
                        itemList.add(newSchedule);
                        timetable.add(itemList);
                    }
                }
            }
        });
    }

    private Schedule getStructed(QueryDocumentSnapshot document) {
        ScheduleInfo temp = document.toObject(ScheduleInfo.class);
        Time StartTime = new Time(temp.getStartTimeHour(), temp.getStartTimeMinute());
        Time EndTime = new Time(temp.getEndTimeHour(), temp.getEndTimeMinute());
        Schedule newSchedule = new Schedule();
        newSchedule.setStartTime(StartTime);
        newSchedule.setEndTime(EndTime);
        newSchedule.setDay(temp.getDay());
        newSchedule.setClassPlace(temp.getClassPlace());
        newSchedule.setClassTitle(temp.getClassTitle());
        newSchedule.setProfessorName(temp.getProfessorName());
        return newSchedule;
    }

}