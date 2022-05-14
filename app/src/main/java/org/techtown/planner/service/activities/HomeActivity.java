package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.planner.R;
import org.techtown.planner.service.fragments.GroupFragment;
import org.techtown.planner.service.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment ScheduleFragment;
    Fragment GroupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Fragment 초기화
        ScheduleFragment = new HomeFragment();
        GroupFragment = new GroupFragment();

        // 최초 fragment 화면은 schedule fragment로 띄워준다.
        Init_Frag_Screen();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_schedule:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout,ScheduleFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_group:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout,GroupFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_myprofile:
                        Log.e("temp", "프로필 창 띄우기");
                        return true;
                }
                return false;
            }
        });
    }

    private void Init_Frag_Screen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout,ScheduleFragment).commitAllowingStateLoss();
    }
}