package org.techtown.planner.domain.member;

public class Member {
    private String Email;
    private String Nickname;

    public Member(String email, String nickname) {
        Email = email;
        Nickname = nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }
}
