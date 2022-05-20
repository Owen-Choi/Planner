package org.techtown.planner.service.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

import org.techtown.planner.R;

public class EachGroupActivity extends AppCompatActivity {

    public TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_group);
        int position = getIntent().getIntExtra("i",1);
        text1 = findViewById(R.id.text1);
        text1.setText(String.valueOf(position));
        System.out.println(position);
    }
}