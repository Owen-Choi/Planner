package org.techtown.planner.service.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.techtown.planner.R;

import java.util.ArrayList;

public class schedule_test_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_test);
        TimetableView timetable = findViewById(R.id.timetable);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        Schedule schedule = new Schedule();
        schedule.setClassTitle("Data Structure"); // sets subject
        schedule.setDay(2);     // 요일은 setDay로 조정한다.
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        Schedule schedule2 = new Schedule();
        schedule2.setClassTitle("Operating System"); // sets subject
        schedule2.setDay(0);     // 요일은 setDay로 조정한다.
        schedule2.setClassPlace("AI-402"); // sets place
        schedule2.setProfessorName("Yoo Joon"); // sets professor
        schedule2.setStartTime(new Time(14,0)); // sets the beginning of class time (hour,minute)
        schedule2.setEndTime(new Time(16,0)); // sets the end of class time (hour,minute)
        schedules.add(schedule2);
//.. add one or more schedules
        timetable.add(schedules);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Log.e("temp", "time : " + schedules.get(idx).getStartTime().getHour() +
                        " : " + schedules.get(idx).getStartTime().getMinute());
//                for(int i=0; i<schedules.size(); i++) {
//                    Log.e("temp", "schedule " + i + " : " + schedules.get(i).getStartTime().getHour());
//                }
            }
        });
    }
}