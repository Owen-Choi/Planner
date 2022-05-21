package org.techtown.planner.service.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.techtown.planner.R;

import java.util.ArrayList;

public class GroupScheduleSample extends AppCompatActivity {

    ArrayList<String> userList = new ArrayList<>();
    ArrayList<Schedule> available = new ArrayList<>();
    ArrayList<Schedule> unavailable = new ArrayList<>();
    static final Time StartTime = new Time(9, 0);
    static final Time EndTime = new Time(20, 0);
    private TimetableView timetable;

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
        available.add(TuesdaySchedule);

        Schedule WednesSchedule = new Schedule();
        WednesSchedule.setDay(2);
        WednesSchedule.setStartTime(StartTime);
        WednesSchedule.setEndTime(EndTime);
        available.add(WednesSchedule);

        Schedule ThursSchedule = new Schedule();
        ThursSchedule.setDay(3);
        ThursSchedule.setStartTime(StartTime);
        ThursSchedule.setEndTime(EndTime);
        available.add(ThursSchedule);

        Schedule FridaySchedule = new Schedule();
        FridaySchedule.setDay(4);
        FridaySchedule.setStartTime(StartTime);
        FridaySchedule.setEndTime(EndTime);
        available.add(FridaySchedule);

        timetable.add(available);

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

        timetable.add(unavailable);
    }
}