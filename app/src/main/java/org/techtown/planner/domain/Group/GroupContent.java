package org.techtown.planner.domain.Group;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupContent {
    private String gname;
    private String masterID;
    private String GroupPassword;
    private ArrayList<String> userList;
    private int MaxNum;

    public GroupContent(){   }

    public GroupContent(String gname, String masterID, String groupPassword, ArrayList<String> userList, int maxNum) {
        this.gname = gname;
        this.masterID = masterID;
        GroupPassword = groupPassword;
        this.userList = userList;
        MaxNum = maxNum;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getMasterID() {
        return masterID;
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID;
    }

    public String getGroupPassword() {
        return GroupPassword;
    }

    public void setGroupPassword(String groupPassword) {
        GroupPassword = groupPassword;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }

    public int getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(int maxNum) {
        MaxNum = maxNum;
    }
}
