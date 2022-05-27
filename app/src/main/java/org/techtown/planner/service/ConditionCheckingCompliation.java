package org.techtown.planner.service;

import android.util.Log;
import android.widget.Toast;

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
    private boolean already_joined(ArrayList<String> userList, String myUid) {
        for (String memberUid : userList) {
            if(myUid.equals(memberUid))
                // 이미 가입한 그룹.
                return true;
        }
        return false;
    }

}
