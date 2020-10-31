package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AddEditReminderActivity extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddReminderActivity";

    public static final String extra_reminder_name = "com.example.medregister.EXTRA_REMINDER_NAME";
    public static final String extra_date = "com.example.medregister.EXTRA_DATE";
    public static final String extra_id = "com.example.medregister.EXTRA_ID";

    private EditText editTextReminderName;
    private EditText editTextDate;
    private String sDate;
    private int mHour, mYear, mMonth, mMinute, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);


        editTextReminderName = findViewById(R.id.edit_reminder_name);
        editTextDate = findViewById(R.id.date);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Setting the date");
                setDate();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        addEditReminderActivity();
    }

    private void saveReminder() {

        String textReminder = editTextReminderName.getText().toString();
        String textDate = editTextDate.getText().toString();

        if (textReminder.trim().isEmpty()) {
            Toast.makeText(this, "Please insert reminder", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(extra_reminder_name, textReminder);
        data.putExtra(extra_date, textDate);


        int id = getIntent().getIntExtra(extra_id, -1);
        if (id != -1) {
            data.putExtra(extra_id, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_reminder_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_reminder:
                saveReminder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addEditReminderActivity() {
        Intent intent = getIntent();
        if (intent.hasExtra(extra_id)) {
            setTitle("Edit Reminder");
            editTextReminderName.setText(intent.getStringExtra(extra_reminder_name));
            editTextDate.setText(intent.getStringExtra(extra_date));
        } else {
            setTitle("Add Reminder");

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
}