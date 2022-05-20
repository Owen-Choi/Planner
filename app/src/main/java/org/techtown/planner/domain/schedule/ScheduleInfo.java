package org.techtown.planner.domain.schedule;

import java.io.Serializable;

public class ScheduleInfo implements Serializable {
    int StartTimeHour;
    int StartTimeMinute;
    int EndTimeHour;
    int EndTimeMinute;
    String uid;
    String classTitle;
    String classPlace;
    String professorName;
    int day;

    public ScheduleInfo() {
    }

    public ScheduleInfo(int startTimeHour, int startTimeMinute, int endTimeHour, int endTimeMinute,
                        String uid, String classTitle, String classPlace, String professorName, int day) {
        StartTimeHour = startTimeHour;
        StartTimeMinute = startTimeMinute;
        EndTimeHour = endTimeHour;
        EndTimeMinute = endTimeMinute;
        this.uid = uid;
        this.classTitle = classTitle;
        this.classPlace = classPlace;
        this.professorName = professorName;
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

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
