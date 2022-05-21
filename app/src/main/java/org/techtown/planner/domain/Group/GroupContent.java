package org.techtown.planner.domain.Group;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GroupContent {
    private String gname;

    public GroupContent(){
    }

    public GroupContent(String gname)
    {
        this.gname = gname;
    }

    public String getName() {
        return gname;
    }

    public void setName(String gname) {
        this.gname = gname;
    }
}
