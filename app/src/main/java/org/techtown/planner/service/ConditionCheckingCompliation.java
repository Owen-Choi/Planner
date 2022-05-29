package org.techtown.planner.service;

import android.util.Log;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;

import org.techtown.planner.domain.Group.GroupContent;

import java.util.ArrayList;

public class ConditionCheckingCompliation {
    // 애플리케이션 전반에서 사용되는 condition checking 메소드들을 모아놓은 클래스
    public ConditionCheckingCompliation() {
    }

    // groupAddActivity의 그룹생성 조건
    public boolean CheckCondition(String Name, int MaxNum, String Pw) {
        if(Name.length() <= 2 || MaxNum < 1 || MaxNum > 9 || Pw.length() <= 3) {
            return false;
        }
        return true;
    }

    // EachGroupActivity의 time fix 권한관련 조건
    public boolean permissionCheck(GroupContent master, String memberUid) {
        if(memberUid.equals(master.getMasterID()))
            return true;
        return false;
    }

    // GroupFragment의 비밀번호 확인 로직
    public boolean PasswordChecker(GroupContent fromDB, String password) {
        if(!fromDB.getGroupPassword().equals(password))
            return false;
        return true;
    }

    // GroupFragment의 최대인원 초과 여부 확인 로직
    public boolean isAvailable(GroupContent temp, int currentMemberSize) {
        if(currentMemberSize <
                temp.getMaxNum()) {
            return true;
        }
        return false;
    }

    // GroupFragment의 이미 참여한 그룹인지 확인하는 로직
    public boolean already_joined(GroupContent groupContent, String myUid) {
        for (String memberUid : groupContent.getUserList()) {
            if(myUid.equals(memberUid))
                // 이미 가입한 그룹.
                return true;
        }
        return false;
    }


    // EachGroupActivity의 시간 중복 확인 로직 from 채영
    public boolean checkFixTime(int[][] check_time, Schedule ss){
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

    // fixActivity의 일정 픽스 로직
    public boolean fixCondition(String groupName, String groupPlace, String meeting){
        if(groupName.length() <=0 || groupPlace.length() <=0 || meeting.length() <= 0)
            return false;
        return true;
    }

    // 로그인 조건 확인 로직
    public boolean SignInConditionChecker(String EmailValue, String PasswordValue) {
        if(EmailValue.length() != 0 && PasswordValue.length() != 0)
            return true;
        else
            return false;
    }

    // 회원가입 조건 확인 로직
    public boolean SignUpConditionChecker(String EmailValue, String PasswordValue, String NicknameValue,
                                     String NameValue, String BirthDateValue, String gender) {
        if (EmailValue.length() != 0 && PasswordValue.length() != 0 && NicknameValue.length() != 0
                && NameValue.length() != 0 && BirthDateValue.length() != 0 && gender.length() != 0)
            return true;
        else
            return false;
    }

}
