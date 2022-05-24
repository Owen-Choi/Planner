package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.techtown.planner.R;

import java.util.ArrayList;

public class EachGroupActivity extends AppCompatActivity {
    public static final int RESULT_OK_ADD = 1;
    public TextView text1;
    private TimetableView GroupTimetable;
    ArrayList<Schedule> available = new ArrayList<>();
    ArrayList<Schedule> unavailable = new ArrayList<>();
    static final Time StartTime = new Time(9, 0);
    static final Time EndTime = new Time(20, 0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_each_group);
        int position = getIntent().getIntExtra("i",1);
        text1 = findViewById(R.id.text1);
        String temp = String.valueOf(position) + "번 그룹";
        text1.setText(temp);
        GroupTimetable = findViewById(R.id.groupTimetable);
        tempInit();
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
                Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show();
                //TODO: schedule 취합 알고리즘 부르기
                break;
            case R.id.menu_fix:
                Toast.makeText(this, "그룹 시간 정하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EachGroupActivity.this, FixActivity.class);

                intent.putExtra("mode", RESULT_OK_ADD);
                startActivityForResult(intent, 0);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

        private void tempInit() {
            for(int i=0; i<5; i++) {
                Schedule newSchedule = new Schedule();
                newSchedule.setDay(i);
                newSchedule.setStartTime(StartTime);
                newSchedule.setEndTime(EndTime);
                available.add(newSchedule);
            }

            GroupTimetable.add(available);

            /////////////////////////////////////////////////////////
            // 여기서 부터는 그룹원들의 시간표 취합한 리스트.
            // 위에서 시간표의 모든 시간에 일정을 등록해놓으면 아래에서 그 일정 위에 그룹원들의 일정을 덮어쓰는 방식.
            Schedule tempSchedule1 = new Schedule();
            tempSchedule1.setDay(0);
            tempSchedule1.setStartTime(new Time(13, 0));
            tempSchedule1.setEndTime(new Time(15, 0));
            tempSchedule1.setClassTitle("Operating System");
            tempSchedule1.setClassPlace("IT-601");
            tempSchedule1.setProfessorName("Yoo Joon");

            Schedule tempSchedule2 = new Schedule();
            tempSchedule2.setDay(1);
            tempSchedule2.setStartTime(new Time(10, 0));
            tempSchedule2.setEndTime(new Time(12, 0));
            tempSchedule2.setClassTitle("Robotics");
            tempSchedule2.setClassPlace("IT-304");
            tempSchedule2.setProfessorName("YongJoo Jeong");

            Schedule tempSchedule3 = new Schedule();
            tempSchedule3.setDay(3);
            tempSchedule3.setStartTime(new Time(13, 0));
            tempSchedule3.setEndTime(new Time(15, 0));
            tempSchedule3.setClassTitle("Mobile Programming");
            tempSchedule3.setClassPlace("AI-415");
            tempSchedule3.setProfessorName("JongHyeon Ahn");

            Schedule tempSchedule4 = new Schedule();
            tempSchedule4.setDay(3);
            tempSchedule4.setStartTime(new Time(9, 0));
            tempSchedule4.setEndTime(new Time(13, 0));
            tempSchedule4.setClassTitle("Firebase");
            tempSchedule4.setClassPlace("Yatap");
            tempSchedule4.setProfessorName("Cheolwoong Choi");

            Schedule tempSchedule5 = new Schedule();
            tempSchedule5.setDay(2);
            tempSchedule5.setStartTime(new Time(9, 0));
            tempSchedule5.setEndTime(new Time(13, 30));
            tempSchedule5.setClassTitle("Database");
            tempSchedule5.setClassPlace("Yatap");
            tempSchedule5.setProfessorName("Cheolwoong Choi");

            unavailable.add(tempSchedule1);
            unavailable.add(tempSchedule2);
            unavailable.add(tempSchedule3);
            unavailable.add(tempSchedule4);
            unavailable.add(tempSchedule5);

            GroupTimetable.add(unavailable);
        }
}
