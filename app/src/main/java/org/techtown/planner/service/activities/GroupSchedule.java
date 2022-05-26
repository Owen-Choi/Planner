package org.techtown.planner.service.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;

import java.util.ArrayList;

public class GroupSchedule extends AppCompatActivity {

    ArrayList<String> userList = new ArrayList<>(); //그룹안에 있는 유저들, uid가 들어가는건가
    ArrayList<Schedule> available = new ArrayList<>();
    ArrayList<Schedule> unavailable = new ArrayList<>();
    static final Time StartTime = new Time(9, 0);
    static final Time EndTime = new Time(19, 0);
    private TimetableView timetable;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = firebaseAuth.getCurrentUser();
    private int [][] check_time = new int[5][21];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_from_chae_chae);
        timetable = findViewById(R.id.groupTimetable);
        tempInit();
    }

    private void tempInit() {
        Schedule MondaySchedule = new Schedule();
        MondaySchedule.setDay(0);
        MondaySchedule.setStartTime(StartTime);
        MondaySchedule.setEndTime(EndTime);
        MondaySchedule.setClassTitle("Available");
        available.add(MondaySchedule);

        Schedule TuesdaySchedule = new Schedule();
        TuesdaySchedule.setDay(1);
        TuesdaySchedule.setStartTime(StartTime);
        TuesdaySchedule.setEndTime(EndTime);
        MondaySchedule.setClassTitle("Available");
        available.add(TuesdaySchedule);

        Schedule WednesSchedule = new Schedule();
        WednesSchedule.setDay(2);
        WednesSchedule.setStartTime(StartTime);
        WednesSchedule.setEndTime(EndTime);
        MondaySchedule.setClassTitle("Available");
        available.add(WednesSchedule);

        Schedule ThursSchedule = new Schedule();
        ThursSchedule.setDay(3);
        ThursSchedule.setStartTime(StartTime);
        ThursSchedule.setEndTime(EndTime);
        MondaySchedule.setClassTitle("Available");
        available.add(ThursSchedule);

        Schedule FridaySchedule = new Schedule();
        FridaySchedule.setDay(4);
        FridaySchedule.setStartTime(StartTime);
        FridaySchedule.setEndTime(EndTime);
        MondaySchedule.setClassTitle("Available");
        available.add(FridaySchedule);

        timetable.add(available);

        for(int i = 0; i < check_time.length; i++){
            for(int j = 0; j < check_time[i].length; j++){
                check_time[i][j] = 0;
            }
        }

        //개인시간표를 그룹시간표에 덧붙이기
        addPersonalSchedule();
    }
    private void addPersonalSchedule() {

        Schedule tempSchedule = new Schedule();
        tempSchedule.setDay(2);
        tempSchedule.setStartTime(new Time(9, 0));
        tempSchedule.setEndTime(new Time(13, 30));
        tempSchedule.setClassTitle("Database");
        tempSchedule.setClassPlace("Yatap");
        tempSchedule.setProfessorName("Cheolwoong Choi");

        unavailable.add(tempSchedule);
        timetable.add(unavailable);


        // 파이어스토어의 컬렉션을 참조하는 객체
        CollectionReference productRef = db.collection("Schedule").document(user.getUid())
                .collection("Personal_schedules");

        //get()을 통해서 해당 컬렉션의 정보를 가져옴.
        //Todo : userlist 에 있는 user마다 이 작업을 수행해줘야 함!!
        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //작업이 성공적으로 마쳐졌으면
                if (task.isSuccessful()){
                    //컬렉션 아래에 있는 모든 정보를 가져옴
                    for (QueryDocumentSnapshot document : task.getResult()){
                        //document.getData(), document.getId()등등
                        //Todo: Personal_Schedule collection 밑으로 등록된 document 정보들 어떻게 읽어오지
                        //document정보들 읽어들어와서 unavailable Arraylist에 넣고 timetable에 add

                        // 배열 값 0,1 지정해주기
                        setArrayValue(tempSchedule);
                    }
                } else{}
            }
        });
    }

    // available : 0, unavailable : 1
    // 그룹 시간 fix 할 때 unavailable 체크용으로 사용
    private void setArrayValue(Schedule sample){
        int ch_day = sample.getDay();
        int start_m = sample.getStartTime().getMinute();
        int start_h = sample.getStartTime().getHour();

        int end_m = sample.getEndTime().getMinute();
        int end_h = sample.getEndTime().getHour();

        int start_index, end_index;

        if (start_m == 0){
            start_index = start_h - 9;
        } else {
            start_index = start_h - 8;
        }

        if (end_m == 0){
            end_index = end_h - 9;
        } else {
            end_index = end_h - 8;
        }

        for(int i = start_index; i <= end_index; i++){
            check_time[ch_day][i] = 1;
        }
    }

    //배열은 그룹 스케쥴을 관리하는 배열을 의미
    private boolean checkFixTime(Schedule ss){
        int group_day = ss.getDay();
        int g_start_m = ss.getStartTime().getMinute();
        int g_start_h = ss.getStartTime().getHour();
        int g_end_m = ss.getEndTime().getMinute();
        int g_end_h = ss.getEndTime().getHour();
        boolean check = true;

        int g_start_idx, g_end_idx;

        if (g_start_m == 0){
            g_start_idx = g_start_h - 9;
        } else {
            g_start_idx = g_start_h - 8;
        }

        if (g_end_m == 0){
            g_end_idx = g_end_h - 9;
        } else {
            g_end_idx = g_end_h - 8;
        }
        for(int i = g_start_idx; i <= g_end_idx; i++){
            if(check_time[group_day][i] == 1){
                check = false;
                break;
            }
        }
        return check;
    }
}
