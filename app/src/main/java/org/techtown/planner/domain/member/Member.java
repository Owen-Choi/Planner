package org.techtown.planner.domain.member;

public class Member {
    private String Email;
    private String Pw;
    private String Nickname;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPw() {
        return Pw;
    }

    public void setPw(String pw) {
        Pw = pw;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public Member(String email, String pw, String nickname) {
        Email = email;
        Pw = pw;
        Nickname = nickname;
    }
}
