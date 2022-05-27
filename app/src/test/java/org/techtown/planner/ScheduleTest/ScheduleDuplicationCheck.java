package org.techtown.planner.ScheduleTest;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.techtown.planner.service.ConditionCheckingCompliation;

public class ScheduleDuplicationCheck {

    ConditionCheckingCompliation ccc;
    int[][] check_time = new int[5][21];
    Schedule sampleSchedule;


    @Before
    public void 일정_초기화() {
        ccc = new ConditionCheckingCompliation();
        sampleSchedule = new Schedule();
        sampleSchedule.setDay(1);
        sampleSchedule.setStartTime(new Time(12, 0));
        sampleSchedule.setEndTime(new Time(13, 30));

        for(int i = 0; i < check_time.length; i++){
            for(int j = 0; j < check_time[i].length; j++){
                check_time[i][j] = 0;
            }
        }
    }

    @Test
    public void 일정_중복_확인() {
        Schedule schedule = new Schedule();
        schedule.setStartTime(new Time(11, 30));
        schedule.setEndTime(new Time(14, 0));
        schedule.setDay(1);
        setArrayValue(schedule);
        // 위에서 만든 일정과 겹치는 일정이 있다면 false가 반환되어야 한다.
        Assert.assertFalse(ccc.checkFixTime(check_time, sampleSchedule));
    }

    // 넣은 일정에 대해 해당되는 위치의 Array를 채운다.
    private void setArrayValue(Schedule schedule){
        int ch_day = schedule.getDay();
        int start_m = schedule.getStartTime().getMinute();
        int start_h = schedule.getStartTime().getHour();

        int end_m = schedule.getEndTime().getMinute();
        int end_h = schedule.getEndTime().getHour();

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
}
