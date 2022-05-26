package org.techtown.planner.service;

import android.widget.Toast;

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




}
