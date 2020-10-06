package com.example.medregister.adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.medregister.databases.ReminderRepository;
import com.example.medregister.models.Reminder;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private ReminderRepository reminderRepository;
    private LiveData<List<Reminder>> allReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        reminderRepository = new ReminderRepository(application);
        allReminders = reminderRepository.getAllReminders();
    }

    public void insert(Reminder reminder) {
        reminderRepository.insert(reminder);
    }

    public void update(Reminder reminder) {
        reminderRepository.update(reminder);
    }

    public void delete(Reminder reminder) {
        reminderRepository.delete(reminder);
    }

    public void deleteAllPills() {
        reminderRepository.deleteAllReminders();
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return allReminders;
    }
}
