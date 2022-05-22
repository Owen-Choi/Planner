package org.techtown.planner.domain.Group;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupContent {
    private String gname;
    private ArrayList<Integer> userList;

    public GroupContent(){   }

    public GroupContent(String gname)
    {
        this.gname = gname;
    }

    public GroupContent(String gname, ArrayList userList)
    {
        this.gname = gname;
        this.userList = userList;
    }

    public String getName() {
        return gname;
    }

    public ArrayList getUserList() {
        return userList;
    }

    public void setName(String gname) {
        this.gname = gname;
    }

    public void setUserList(ArrayList userList)
    {
        this.userList = userList;
    }
}
