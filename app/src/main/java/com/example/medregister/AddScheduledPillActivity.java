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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class AddScheduledPillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "AddScheduledPillActivit";
    public static final String EXTRA_NAME = "com.example.medregister.EXTRA_NAME";
    public static final String EXTRA_DATE = "com.example.medregister.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.example.medregister.EXTRA_TIME";
    public static final String EXTRA_DOSE = "com.example.medregister.EXTRA_DOSE";


    private EditText editTextName;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextDose;
    private String sDate;
    private int mHour, mYear, mMonth, mMinute, mDay;
    final int id = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheduled_pill);

        editTextName = findViewById(R.id.edit_text_med_name);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Setting the date");
                setTime();
            }
        });
        editTextTime = findViewById(R.id.edit_text_time);
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

        editTextDose = findViewById(R.id.edit_text_dose);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        setTitle("Schedule pill");
    }

    @Override
    public void onBackPressed() {
        cancelAlarm();
        finish();
    }

    private void saveScheduledPill() {
        String name = editTextName.getText().toString();
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        int dose = Integer.parseInt(editTextDose.getText().toString());

        if (name.trim().isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_DOSE, dose);

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
        switch (item.getItemId()) {
            case R.id.save_scheduled_pill:
                saveScheduledPill();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(Calendar.getInstance());
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void setTime() {
        setDate();
        final Calendar mCurrentTime = Calendar.getInstance();
        mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;

                editTextTime.setText(String.format(Locale.getDefault(), "%d:%d", hourOfDay, minute));

                Calendar c = Calendar.getInstance();

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                startAlarm(c);

            }
        }, mHour, mMinute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        sDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        Toast.makeText(this, sDate, Toast.LENGTH_SHORT).show();
        editTextDate.setText(sDate);
    }

    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}