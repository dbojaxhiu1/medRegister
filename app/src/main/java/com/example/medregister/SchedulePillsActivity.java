package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.medregister.adapters.SchedulePillViewModel;
import com.example.medregister.models.SchedulePill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SchedulePillsActivity extends AppCompatActivity {

    private static final String TAG = "SchedulePillsActivity";
    public static final int ADD_SCHEDULE_PILL_REQUEST = 1;
    private SchedulePillViewModel schedulePillViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pills);
        Log.d(TAG, "onCreate: started.");
        setTitle("Schedule Pills");

        FloatingActionButton fob_schedule_pill = findViewById(R.id.fob_schedule_pill);
        fob_schedule_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulePillsActivity.this, AddScheduledPillActivity.class);
                startActivityForResult(intent, ADD_SCHEDULE_PILL_REQUEST);
            }
        });

        schedulePillViewModel = ViewModelProviders.of(this).get(SchedulePillViewModel.class);
        schedulePillViewModel.getAllScheduledPills().observe(this, new Observer<List<SchedulePill>>() {
            @Override
            public void onChanged(List<SchedulePill> schedulePills) {
                Toast.makeText(SchedulePillsActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
