package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.planner.R;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 바텀 네비게이션 바 이용해서 프레그먼트로?

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_schedule:
                        Log.e("temp", "시간표 fragment 띄우기");
                        return true;
                    case R.id.menu_group:
                        Log.e("temp", "그룹 창 띄우기");
                        return true;
                    case R.id.menu_myprofile:
                        Log.e("temp", "프로필 창 띄우기");
                        return true;
                }
                return false;
            }
        });
    }
}