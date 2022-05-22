package org.techtown.planner.service.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.domain.schedule.ScheduleInfo;

import java.util.ArrayList;

// 전반적으로 변수명 변경함

public class FixActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_OK_ADD = 1;
    private static final String TAG = "fix";
//    public static final int RESULT_OK_EDIT = 2;
//    public static final int RESULT_OK_DELETE = 3;

    private Context context;

//    private Button deleteBtn;
    private Button fixbtn;
    private EditText gnameEdit;
    private EditText gplaceEdit;
    private EditText meetingEdit;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;

    //request mode
    private int mode;

    private Schedule schedule;
    private int editIdx;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = firebaseAuth.getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_time_fix);

        init();
    }

    private void init(){
        this.context = this;
//        deleteBtn = findViewById(R.id.delete_btn);
        fixbtn = findViewById(R.id.fixbtn);
        gnameEdit = findViewById(R.id.group_name_edit);
        gplaceEdit = findViewById(R.id.place_edit);
        meetingEdit = findViewById(R.id.meeting);
        daySpinner = findViewById(R.id.day_spinner);
        startTv = findViewById(R.id.start_time);
        endTv = findViewById(R.id.end_time);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", RESULT_OK_ADD);

        Log.d(TAG, "onCreate: FixActivity");

        //set the default time
        schedule = new Schedule();
        schedule.setStartTime(new Time(10,0));
        schedule.setEndTime(new Time(13,30));

        //TODO: 조건문으로 다 막는 것 -> 아래 onclick 함수에서 적용하자
//        checkMode();
        initView();
    }

//    /** check whether the mode is ADD or EDIT */
//    private void checkMode(){
//        Intent i = getIntent();
//        mode = i.getIntExtra("mode", TimetableActivity.REQUEST_ADD);
//
//        if(mode == TimetableActivity.REQUEST_EDIT){
//            loadScheduleData();
//            deleteBtn.setVisibility(View.VISIBLE);
//        }
//
//    }
    private void initView(){
        fixbtn.setOnClickListener(this);
//        deleteBtn.setOnClickListener(this);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 시계 돌아가는 부분 코드.
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context, listener,schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv.setText(hourOfDay + ":" + minute);
                    schedule.getStartTime().setHour(hourOfDay);
                    schedule.getStartTime().setMinute(minute);
                }
            };
        });
        endTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv.setText(hourOfDay + ":" + minute);
                    schedule.getEndTime().setHour(hourOfDay);
                    schedule.getEndTime().setMinute(minute);
                }
            };
        });
    }

    @Override
    public void onClick(View v) {
        //switch 로 안해도 됨
        switch (v.getId()){
            case R.id.fixbtn:
                //TODO: 여기서 조건문으로 확인해서 막기 (가능한 시간대로만 설정할 수 있도록)
                setResult(0);
                finish();
//                if(mode == TimetableActivity.REQUEST_ADD){
//                    inputDataProcessing();
//                    Intent i = new Intent();
//                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
//                    //you can add more schedules to ArrayList
//                    // 디비에 값 넣어주는 작업 여기서 수행하자.
//                    initAndInsert();
//                    schedules.add(schedule);
//                    i.putExtra("schedules",schedules);
//                    setResult(RESULT_OK_ADD,i);
//                    finish();
//                }
//                else if(mode == TimetableActivity.REQUEST_EDIT){
//                    deleteAndInsert();
//                    Intent i = new Intent();
//                    inputDataProcessing();
//                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
//                    Log.e("woong", "onClick: " + schedule.getClassTitle());
//                    schedules.add(schedule);
//                    i.putExtra("idx",editIdx);
//                    i.putExtra("schedules",schedules);
//                    setResult(RESULT_OK_EDIT,i);
//                    finish();
//                }
                break;
//            case R.id.delete_btn:
//                Intent i = new Intent();
//                deleteFromDB();
//                i.putExtra("idx",editIdx);
//                setResult(RESULT_OK_DELETE, i);
//                finish();
//                break;
        }
    }

    
    private void loadScheduleData(){
        Intent i = getIntent();
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
        schedule = schedules.get(0);
        //들어갈 때는 다른 시간표와 동일하게 들어감
        /*  group name -> class title
            meeting place -> class place
            meeting detail -> professor name
        */
        gnameEdit.setText(schedule.getClassTitle());
        gplaceEdit.setText(schedule.getClassPlace());
        meetingEdit.setText(schedule.getProfessorName());
        daySpinner.setSelection(schedule.getDay());
    }

    private void inputDataProcessing(){
        schedule.setClassTitle(gnameEdit.getText().toString());
        schedule.setClassPlace(gplaceEdit.getText().toString());
        schedule.setProfessorName(meetingEdit.getText().toString());
    }

    // 새 ScheduleInfo 초기화한 후 DB에 값 넣어주는 함수 호출
    private void initAndInsert() {
        ScheduleInfo newSchedule = new ScheduleInfo(
                schedule.getStartTime().getHour(),
                schedule.getStartTime().getMinute(),
                schedule.getEndTime().getHour(),
                schedule.getEndTime().getMinute(),
                user.getUid(),
                schedule.getClassTitle(),
                schedule.getClassPlace(),
                schedule.getProfessorName(),
                schedule.getDay());
//        InsertSchedule(newSchedule);
    }

    //TODO: InsertSchedule 은 아직 (조건문을 완성 시키면 실행
    private void InsertSchedule(ScheduleInfo scheduleInfo) {
        // DB 구조는
        db.collection("Schedule").document(user.getUid())
                .collection("Personal_schedules")
                .document(scheduleInfo.getDay() + " " + scheduleInfo.getClassTitle()).set(scheduleInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.e("woong", "DB 삽입 성공");
                }
            }
        });
    }

//    private void deleteAndInsert() {
//        // 이전 데이터는 잘 들고있다. submit 누르면 이 함수가 호출되고,
//        // 여기서 이 이전 데이터 활용해서 DB에서 기존 값 삭제해주고 바뀐 값으로 다시 넣어주기.
//        // 바뀐 값 다시 넣어주는 기능은 기존에 만들어 둔 InsertSchedule함수가 수행한다.
//        String searchKey = schedule.getDay() + " " + schedule.getClassTitle();
//        db.collection("Schedule").document(user.getUid())
//                .collection("Personal_schedules").document(searchKey).delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Log.e("woong", "삭제 완료");
//                            // 바뀐 데이터 최신화 해주고 다시 넣어주기
//                            inputDataProcessing();
//                            initAndInsert();
//                        }
//                    }
//                });
//    }

//    private void deleteFromDB() {
//        String searchKey = schedule.getDay() + " " + schedule.getClassTitle();
//        db.collection("Schedule").document(user.getUid())
//                .collection("Personal_schedules").document(searchKey).delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Log.e("woong", "삭제 완료");
//                        }
//                    }
//                });
//    }
}
