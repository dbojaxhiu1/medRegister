package com.example.medregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
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
    public static final int EDIT_REMINDER_REQUEST = 2;

    private ReminderViewModel reminderViewModel;
    private RadioButton radioButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Log.d(TAG, "onCreate: started.");
        setTitle(R.string.to_do_activity_title);
        radioButton = findViewById(R.id.radio_button_reminder);
        FloatingActionButton buttonAddReminder = findViewById(R.id.fob_add_reminder);
        buttonAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoActivity.this, AddEditReminderActivity.class);
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
                reminderAdapter.submitList(reminders);
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
                Toast.makeText(ToDoActivity.this, R.string.reminder_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        reminderAdapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reminder reminder) {
                Intent intent = new Intent(ToDoActivity.this, AddEditReminderActivity.class);
                intent.putExtra(AddEditReminderActivity.extra_id, reminder.getId());
                intent.putExtra(AddEditReminderActivity.extra_reminder_name, reminder.getText());
                intent.putExtra(AddEditReminderActivity.extra_date, reminder.getDate());
                startActivityForResult(intent, EDIT_REMINDER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REMINDER_REQUEST && resultCode == RESULT_OK) {
            String reminderText = data.getStringExtra(AddEditReminderActivity.extra_reminder_name);
            String reminderDate = data.getStringExtra(AddEditReminderActivity.extra_date);

            Reminder reminder = new Reminder(reminderText, reminderDate);
            reminderViewModel.insert(reminder);

            Toast.makeText(this, R.string.reminder_saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REMINDER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditReminderActivity.extra_id, -1);
            if (id == -1) {
                Toast.makeText(this, R.string.reminder_couldnt_update, Toast.LENGTH_SHORT).show();
                return;
            }
            String reminderText = data.getStringExtra(AddEditReminderActivity.extra_reminder_name);
            String reminderDate = data.getStringExtra(AddEditReminderActivity.extra_date);

            Reminder reminder = new Reminder(reminderText, reminderDate);
            reminder.setId(id);
            reminderViewModel.update(reminder);
            Toast.makeText(this, R.string.reminder_updated, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.reminder_not_saved, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, R.string.all_reminders_deleted, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /* add in XML layout also:   android:onClick="onRadioButtonClicked"
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_button_reminder:
                if (checked) {
                    radioButton.setChecked(true);
                    Toast.makeText(this, "Radio Button checked", Toast.LENGTH_SHORT).show();
                } else {
                    radioButton.setChecked(false);
                    Toast.makeText(this, "Radio button unchecked", Toast.LENGTH_SHORT).show();
                }
        }
    }*/
}
