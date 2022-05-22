package org.techtown.planner.service.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.planner.R;

public class EachGroupActivity extends AppCompatActivity {
    public static final int RESULT_OK_ADD = 1;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_refresh:
                Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show();
                //TODO: schedule 취합 알고리즘 부르기
                break;
            case R.id.menu_fix:
                Toast.makeText(this, "그룹 시간 정하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EachGroupActivity.this, FixActivity.class);

                intent.putExtra("mode", RESULT_OK_ADD);
                startActivityForResult(intent, 0);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}