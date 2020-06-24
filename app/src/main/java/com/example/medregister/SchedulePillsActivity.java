package com.example.medregister;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SchedulePillsActivity extends AppCompatActivity {


    private static final String TAG = "SchedulePillsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pills);
        Log.d(TAG, "onCreate: started.");
    }
}
