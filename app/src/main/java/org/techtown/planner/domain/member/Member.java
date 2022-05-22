package org.techtown.planner.domain.member;

import java.io.Serializable;

public class Member implements Serializable {
    private String Email;
    private String Nickname;
    private String realName;
    private String gender;
    private String birthDate;

    public Member(String email, String nickname, String realName, String gender, String birthDate) {
        Email = email;
        Nickname = nickname;
        this.realName = realName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public Member() {
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
