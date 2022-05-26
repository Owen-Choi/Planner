package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;
import org.techtown.planner.domain.schedule.ScheduleInfo;

import java.util.ArrayList;

public class EachGroupActivity extends AppCompatActivity {
    public TextView text1;
    private TimetableView GroupTimetable;
    ArrayList<Schedule> available = new ArrayList<>();
    ArrayList<Schedule> unavailable = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GroupContent groupInfo;
    static final Time StartTime = new Time(9, 0);
    static final Time EndTime = new Time(20, 0);
    static final int REQUEST_ADD = 1;
    static final String TAG = "EachGroupActivity";

    // 채영 추가
    private int [][] check_time = new int[5][21];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_each_group);
        Intent intent = getIntent();
        int position = intent.getIntExtra("i",1);
        text1 = findViewById(R.id.groupNameTextView);
        String temp = String.valueOf(position) + "번 그룹";
        text1.setText(temp);
        GroupTimetable = findViewById(R.id.groupTimetable);
        groupInfo = (GroupContent) intent.getSerializableExtra("groupContent");

        for(int i = 0; i < check_time.length; i++){
            for(int j = 0; j < check_time[i].length; j++){
                check_time[i][j] = 0;
            }
        }

        Init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_refresh:
                Init();
                break;
            case R.id.menu_fix:
                timeFixing();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

        private void Init() {
            getMemberSchedule();
        }

        // 일정
        private void getMemberSchedule() {
            ArrayList<String> userList = groupInfo.getUserList();
            for (String uid : userList) {
                db.collection("Schedule").document(uid).collection("Personal_schedules")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ScheduleInfo scheduleInfo = document.toObject(ScheduleInfo.class);
                                Schedule MemberSchedule = new Schedule();
                                MemberSchedule.setDay(scheduleInfo.getDay());
                                MemberSchedule.setStartTime(
                                        new Time(scheduleInfo.getStartTimeHour(), scheduleInfo.getStartTimeMinute()));
                                MemberSchedule.setClassTitle(scheduleInfo.getClassTitle());
                                MemberSchedule.setEndTime(
                                        new Time(scheduleInfo.getEndTimeHour(), scheduleInfo.getEndTimeMinute()));
                                unavailable.add(MemberSchedule);
                                Log.e(TAG, "onComplete: ");
                                setArrayValue(MemberSchedule);
                            }
                            GroupTimetable.add(unavailable);
                        }
                    }
                });
            }
        }

        private void timeFixing() {
            if(permissionCheck()) {
                // 방장이라면 시간을 고정할 수 있다.
                Intent intent = new Intent(EachGroupActivity.this, FixActivity.class);
                intent.putExtra("mode", REQUEST_ADD);
                startActivityForResult(intent, REQUEST_ADD);
            } else {
                StartToast("권한이 없습니다.");
            }
        }

        private boolean permissionCheck() {
            if(user.getUid().equals(groupInfo.getMasterID()))
                return true;
            return false;
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD) {
            if (resultCode == FixActivity.RESULT_OK_ADD) {
                Schedule schedule = (Schedule)data.getSerializableExtra("schedule");
                if(checkFixTime(schedule)) {
                    // 그룹원들한테 픽스된 스케쥴 넣어주기
                    scatterScheduleToMember(schedule);
                    Init();
                }
                else
                    StartToast("추가 불가능");
            } else if(resultCode == FixActivity.RESULT_FAIL_ADD) {
                Log.e(TAG, "조건 불만족으로 추가 실패");
                StartToast("빈칸을 확인해주세요.");
            }
        }
    }

    //////////////////////////////////////////////////////
    // 채영 로직
    private void setArrayValue(Schedule sample){
        int ch_day = sample.getDay();
        int start_m = sample.getStartTime().getMinute();
        int start_h = sample.getStartTime().getHour();

        int end_m = sample.getEndTime().getMinute();
        int end_h = sample.getEndTime().getHour();

        // 인덱스 예외 방지용, 철웅 추가
        if(IndexOutOfBoundsCheck(start_h, start_m, end_h, end_m))
            return;

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

    // 시작 시간이나 끝나는 시간이 0이면 index가 음수가 돼서 IndexOutOfBounds 예외 발생.
    private boolean IndexOutOfBoundsCheck(int startH, int startM, int endH, int endM) {
        return startH == 0 || startM == 0 || endH == 0 || endM == 0;
    }

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
    //////////////////////////////////////////////////////

    private void scatterScheduleToMember(Schedule schedule) {
        // db 돌면서 회원들한테 스케쥴 뿌려주기
        ArrayList<String> userList = groupInfo.getUserList();
        String documentName = schedule.getDay() + " " + schedule.getClassTitle();
        for (String memberUid : userList) {
            db.collection("Schedule").document(memberUid).collection("Personal_schedules")
                    .document(documentName).set(getScheduleInfo(schedule)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.e(TAG, "onComplete: " + memberUid + " 에게 스케쥴을 추가하였습니다.");
                }
            });
        }
    }

    private ScheduleInfo getScheduleInfo(Schedule schedule) {
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
        return newSchedule;
    }

        private void StartToast(String msg) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
}
