package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.planner.R;
import org.techtown.planner.service.fragments.AddFragment;
import org.techtown.planner.service.fragments.GroupFragment;
import org.techtown.planner.service.fragments.HomeFragment;
import org.techtown.planner.service.fragments.MyGroupFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    // fragments
    Fragment ScheduleFragment;
    Fragment GroupFragment;
//    Fragment AddFragment;
    Fragment MyGroupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Fragment 초기화
        ScheduleFragment = new HomeFragment();
        GroupFragment = new GroupFragment();
//        AddFragment = new AddFragment();
        MyGroupFragment = new MyGroupFragment();

        // 최초 fragment 화면은 schedule fragment로 띄워준다.
        Init_Frag_Screen();
        // navigation view 초기화 및 클릭 이벤트 설정
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
//                    case R.id.menu_plus:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.main_layout, AddFragment).commitAllowingStateLoss();
//                        return true;
                    case R.id.menu_my:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, MyGroupFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });
    }

    // menu 관련 함수들
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        StartActivity(SettingsActivity.class);
        return super.onOptionsItemSelected(item);
    }

    // 처음 fragment 설정하는 함수
    private void Init_Frag_Screen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout,ScheduleFragment).commitAllowingStateLoss();
    }

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}