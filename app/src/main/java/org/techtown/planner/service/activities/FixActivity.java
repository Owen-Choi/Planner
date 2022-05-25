package org.techtown.planner.service.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.planner.R;
import org.techtown.planner.domain.schedule.ScheduleInfo;

import java.util.ArrayList;

// 전반적으로 변수명 변경함

public class FixActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_FAIL_ADD = 2;
    private static final String TAG = "fix";
//    public static final int RESULT_OK_EDIT = 2;
//    public static final int RESULT_OK_DELETE = 3;

    private Context context;

//    private Button deleteBtn;
    private Button fixbtn;
    private EditText gnameEdit;
    private EditText gplaceEdit;
    private EditText meetingEdit;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;

    //request mode
    private int mode;

    private Schedule schedule;
    private int editIdx;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = firebaseAuth.getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_time_fix);

        init();
    }

    private void init(){
        this.context = this;
        fixbtn = findViewById(R.id.fixbtn);
        gnameEdit = findViewById(R.id.group_name_edit);
        gplaceEdit = findViewById(R.id.place_edit);
        meetingEdit = findViewById(R.id.meeting);
        daySpinner = findViewById(R.id.day_spinner);
        startTv = findViewById(R.id.start_time);
        endTv = findViewById(R.id.end_time);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", RESULT_OK_ADD);

        Log.d(TAG, "onCreate: FixActivity");
        schedule = new Schedule();
        schedule.setStartTime(new Time(10,0));
        schedule.setEndTime(new Time(13,30));

        initView();
    }

    private void initView(){
        fixbtn.setOnClickListener(this);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context, listener,schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv.setText(hourOfDay + ":" + minute);
                    schedule.getStartTime().setHour(hourOfDay);
                    schedule.getStartTime().setMinute(minute);
                }
            };
        });
        endTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv.setText(hourOfDay + ":" + minute);
                    schedule.getEndTime().setHour(hourOfDay);
                    schedule.getEndTime().setMinute(minute);
                }
            };
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fixbtn) {
            // 먼저 빈칸들이 다 체크가 되었는지 확인한다.
            if(inputDataProcessing()) {
                Intent i = new Intent();
                i.putExtra("schedule", schedule);
                setResult(RESULT_OK_ADD, i);
                finish();
            } else {
                setResult(RESULT_FAIL_ADD);
                finish();
            }
        }
    }


    private boolean inputDataProcessing(){
        if(gnameEdit.getText().toString().length() <=0 ||
                gplaceEdit.getText().toString().length() <= 0 ||
                meetingEdit.getText().toString().length() <= 0)
            return false;
        else {
            schedule.setClassTitle(gnameEdit.getText().toString());
            schedule.setClassPlace(gplaceEdit.getText().toString());
            schedule.setProfessorName(meetingEdit.getText().toString());
            return true;
        }
    }

}
