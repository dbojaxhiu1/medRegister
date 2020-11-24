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
    public static final int EDIT_SCHEDULE_PILL_REQUEST = 2;
    private SchedulePillViewModel schedulePillViewModel;
    final int request_id = (int) System.currentTimeMillis();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate view
        setContentView(R.layout.activity_schedule_pills);
        Log.d(TAG, "onCreate: started.");
        setTitle(R.string.schedule_pills_title);

        //create a floating action button variable
        FloatingActionButton fob_schedule_pill = findViewById(R.id.fob_schedule_pill);
        fob_schedule_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulePillsActivity.this, AddEditSchedulePillsActivity.class);
                startActivityForResult(intent, ADD_SCHEDULE_PILL_REQUEST);
            }
        });

        //reference Recycler View
        RecyclerView recyclerView = findViewById(R.id.recyclerView_schedule_pill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //call recycler view
        recyclerView.setHasFixedSize(true);

        //adapter
        final SchedulePillAdapter adapter = new SchedulePillAdapter();
        //pass adapter to recycler view
        recyclerView.setAdapter(adapter);


        schedulePillViewModel = ViewModelProviders.of(this).get(SchedulePillViewModel.class);
        schedulePillViewModel.getAllScheduledPills().observe(this, new Observer<List<SchedulePill>>() {
            @Override
            public void onChanged(List<SchedulePill> schedulePills) {
                adapter.setScheduledPills(schedulePills);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cancelAlarm();
                schedulePillViewModel.delete(adapter.getScheduledPillAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SchedulePillsActivity.this, R.string.scheduled_pill_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new SchedulePillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SchedulePill pill) {
                Intent intent = new Intent(SchedulePillsActivity.this, AddEditSchedulePillsActivity.class);
                intent.putExtra(AddEditSchedulePillsActivity.EXTRA_ID, pill.getId());
                intent.putExtra(AddEditSchedulePillsActivity.EXTRA_NAME, pill.getPillName());
                intent.putExtra(AddEditSchedulePillsActivity.EXTRA_TIME, pill.getTime());
                intent.putExtra(AddEditSchedulePillsActivity.EXTRA_DOSE, pill.getDose());
                startActivityForResult(intent, EDIT_SCHEDULE_PILL_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SCHEDULE_PILL_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_NAME);
            String time = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_TIME);
            String dose = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_DOSE);

            SchedulePill schedulePill = new SchedulePill(name, time, dose);
            schedulePillViewModel.insert(schedulePill);

            Toast.makeText(this, R.string.pill_scheduled, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_SCHEDULE_PILL_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditSchedulePillsActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Scheduled pill not updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_NAME);
            String time = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_TIME);
            String dose = data.getStringExtra(AddEditSchedulePillsActivity.EXTRA_DOSE);

            SchedulePill pill = new SchedulePill(name, time, dose);
            pill.setId(id);
            schedulePillViewModel.update(pill);
            Toast.makeText(this, "Scheduled pill updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.pill_not_scheduled, Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, SchedulePill.getRequestId(), intent, 0);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }
}
