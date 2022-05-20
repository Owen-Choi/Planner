package org.techtown.planner.domain.schedule;

public class ScheduleInfo {
    int StartTimeHour;
    int StartTimeMinute;
    int EndTimeHour;
    int EndTimeMinute;
    String uid;
    String className;
    int day;

    public ScheduleInfo(int startTimeHour, int startTimeMinute, int endTimeHour, int endTimeMinute, String uid, String className, int day) {
        StartTimeHour = startTimeHour;
        StartTimeMinute = startTimeMinute;
        EndTimeHour = endTimeHour;
        EndTimeMinute = endTimeMinute;
        this.uid = uid;
        this.className = className;
        this.day = day;
    }

    public int getStartTimeHour() {
        return StartTimeHour;
    }

    public void setStartTimeHour(int startTimeHour) {
        StartTimeHour = startTimeHour;
    }

    public int getStartTimeMinute() {
        return StartTimeMinute;
    }

    public void setStartTimeMinute(int startTimeMinute) {
        StartTimeMinute = startTimeMinute;
    }

    public int getEndTimeHour() {
        return EndTimeHour;
    }

    public void setEndTimeHour(int endTimeHour) {
        EndTimeHour = endTimeHour;
    }

    public int getEndTimeMinute() {
        return EndTimeMinute;
    }

    public void setEndTimeMinute(int endTimeMinute) {
        EndTimeMinute = endTimeMinute;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
