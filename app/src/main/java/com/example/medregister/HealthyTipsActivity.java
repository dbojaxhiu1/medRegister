package com.example.medregister;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HealthyTipsActivity extends AppCompatActivity {

    private static final String TAG = "HealthyTipsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthytips);
        Log.d(TAG, "onCreate: started.");
        setTitle(getString(R.string.healthy_tips_title));
    }
}
