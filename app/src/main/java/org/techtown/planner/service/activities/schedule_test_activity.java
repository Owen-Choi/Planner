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
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);
//.. add one or more schedules
        timetable.add(schedules);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Log.e("temp", "time : " + schedules.get(idx).getStartTime().getHour() +
                        " : " + schedules.get(idx).getStartTime().getMinute());
            }
        });
    }
}