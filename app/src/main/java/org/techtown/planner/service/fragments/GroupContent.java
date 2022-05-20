package org.techtown.planner.service.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GroupContent {
    private String name;

    public GroupContent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
