package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.example.medregister.adapters.ReminderAdapter;
import com.example.medregister.adapters.ReminderViewModel;
import com.example.medregister.models.Reminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {

    private static final String TAG = "ToDoActivity";

    public static final int ADD_REMINDER_REQUEST = 1;

    private ReminderViewModel reminderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Log.d(TAG, "onCreate: started.");
        setTitle("To Do Activities");

        FloatingActionButton buttonAddReminder = findViewById(R.id.fob_add_reminder);
        buttonAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoActivity.this, AddReminderActivity.class);
                startActivityForResult(intent, ADD_REMINDER_REQUEST);

            }
        });

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
                reminderViewModel.delete(reminderAdapter.getReminderAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ToDoActivity.this, "Reminder deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REMINDER_REQUEST && resultCode == RESULT_OK) {
            String reminderText = data.getStringExtra(AddReminderActivity.extra_reminder_name);
            String reminderDate = data.getStringExtra(AddReminderActivity.extra_date);

            Reminder reminder = new Reminder(reminderText, reminderDate);
            reminderViewModel.insert(reminder);

            Toast.makeText(this, "Reminder saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Reminder not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.to_do_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_reminders:
                reminderViewModel.deleteAllReminders();
                Toast.makeText(this, "All reminders deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
