package com.example.medregister;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AddEditSchedulePillsActivity extends AppCompatActivity {

    public static final String TAG = "AddScheduledPillActivit";
    public static final String EXTRA_NAME = "com.example.medregister.EXTRA_NAME";
    public static final String EXTRA_TIME = "com.example.medregister.EXTRA_TIME";
    public static final String EXTRA_DOSE = "com.example.medregister.EXTRA_DOSE";
    public static final String EXTRA_ID = "com.example.medregister.EXTRA_ID";

    private EditText editTextName;
    private EditText editTextTime;
    private EditText editTextDose;
    private String sDate;
    private int mHour, mMinute;
    final int id = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_add_scheduled_pill);

        // Get references to UI widgets
        editTextName = findViewById(R.id.edit_text_med_name);
        editTextTime = findViewById(R.id.edit_text_time);
        editTextDose = findViewById(R.id.edit_text_dose);

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Setting the time");
                setTime();
            }
        });

        Button buttonCancelAlarm = findViewById(R.id.cancel_scheduled_pill);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                finish();
            }
        });


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        addEditScheduledPill();
    }

    @Override
    public void onBackPressed() {
        cancelAlarm();
        finish();
    }

    private void saveScheduledPill() {
        String name = editTextName.getText().toString();
        String time = editTextTime.getText().toString();
        String dose = editTextDose.getText().toString();

        if (name.trim().isEmpty() || time.isEmpty() || dose.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }
        // create intent with additional info stored as extras
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_DOSE, dose);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_schedule_pill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_scheduled_pill) {
            saveScheduledPill();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setTime() {
        final Calendar mCurrentTime = Calendar.getInstance();
        mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                String sMinute;
                if (minute < 10)
                    sMinute = "0" + minute;
                else
                    sMinute = "" + minute;

                editTextTime.setText(String.format(Locale.getDefault(), "%d:%s", hourOfDay, sMinute));

                Calendar c = Calendar.getInstance();

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                startAlarm(c);

            }
        }, mHour, mMinute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            assert alarmManager != null;
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }


    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }

    public void addEditScheduledPill() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit scheduled pill");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextTime.setText(intent.getStringExtra(EXTRA_TIME));
            editTextDose.setText(intent.getStringExtra(EXTRA_DOSE));
        } else {
            setTitle("Schedule pill");
        }
    }


}