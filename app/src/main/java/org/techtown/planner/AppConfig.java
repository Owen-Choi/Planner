package org.techtown.planner;

import org.techtown.planner.domain.member.MemberService;
import org.techtown.planner.domain.member.MemberServiceImpl;

import java.io.Serializable;

public class AppConfig implements Serializable {
    public MemberService memberService() {
        return new MemberServiceImpl();
    }
}
