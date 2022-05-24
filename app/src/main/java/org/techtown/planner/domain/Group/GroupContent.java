package org.techtown.planner.domain.Group;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupContent {
    private String gname;
    private String masterID;
    private ArrayList<Integer> userList;

    public GroupContent(){   }

    public GroupContent(String gname)
    {
        this.gname = gname;
    }

    public GroupContent(String gname, String masterID, ArrayList<Integer> userList) {
        this.gname = gname;
        this.masterID = masterID;
        this.userList = userList;
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

    public ArrayList<Integer> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<Integer> userList) {
        this.userList = userList;
    }
}
