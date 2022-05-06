package org.techtown.planner;

import org.techtown.planner.domain.member.MemberService;
import org.techtown.planner.domain.member.MemberServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl();
    }
}
