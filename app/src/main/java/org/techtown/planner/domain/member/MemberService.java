package org.techtown.planner.domain.member;

public interface MemberService {
    Boolean SignUp(Member member);
    Boolean SignIn(String Email, String Pw);
    void delete_User();
}
