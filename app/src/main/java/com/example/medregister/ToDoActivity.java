package com.example.medregister;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medregister.adapters.ReminderAdapter;
import com.example.medregister.adapters.ReminderViewModel;
import com.example.medregister.models.Reminder;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {

    private static final String TAG = "ToDoActivity";

    private ReminderViewModel reminderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Log.d(TAG, "onCreate: started.");
        setTitle("To Do Activities");

        RecyclerView recyclerView = findViewById(R.id.recyclerView_toDo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter reminderAdapter = new ReminderAdapter();
        recyclerView.setAdapter(reminderAdapter);

        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                reminderAdapter.setReminders(reminders);
                Toast.makeText(ToDoActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
