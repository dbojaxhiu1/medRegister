package com.example.medregister;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.adapters.SchedulePillAdapter;
import com.example.medregister.adapters.SchedulePillViewModel;
import com.example.medregister.models.SchedulePill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SchedulePillsActivity extends AppCompatActivity {

    private static final String TAG = "SchedulePillsActivity";
    public static final int ADD_SCHEDULE_PILL_REQUEST = 1;
    private SchedulePillViewModel schedulePillViewModel;
    final int id = (int) System.currentTimeMillis();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pills);
        Log.d(TAG, "onCreate: started.");
        setTitle(R.string.schedule_pills_title);

        FloatingActionButton fob_schedule_pill = findViewById(R.id.fob_schedule_pill);
        fob_schedule_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulePillsActivity.this, AddScheduledPillActivity.class);
                startActivityForResult(intent, ADD_SCHEDULE_PILL_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView_schedule_pill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SchedulePillAdapter adapter = new SchedulePillAdapter();
        recyclerView.setAdapter(adapter);


        schedulePillViewModel = ViewModelProviders.of(this).get(SchedulePillViewModel.class);
        schedulePillViewModel.getAllScheduledPills().observe(this, new Observer<List<SchedulePill>>() {
            @Override
            public void onChanged(List<SchedulePill> schedulePills) {
                adapter.setScheduledPills(schedulePills);
            }
        });
        // when I delete it doesn't cancel alarm, request ID is the problem here
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                schedulePillViewModel.delete(adapter.getScheduledPillAt(viewHolder.getAdapterPosition()));
                cancelAlarm();
                Toast.makeText(SchedulePillsActivity.this, R.string.scheduled_pill_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SCHEDULE_PILL_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddScheduledPillActivity.EXTRA_NAME);
            String date = data.getStringExtra(AddScheduledPillActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddScheduledPillActivity.EXTRA_TIME);
            int dose = data.getIntExtra(AddScheduledPillActivity.EXTRA_DOSE, 1);

            SchedulePill schedulePill = new SchedulePill(name, date, time, dose);
            schedulePillViewModel.insert(schedulePill);

            Toast.makeText(this, R.string.pill_scheduled, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.pill_not_scheduled, Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
