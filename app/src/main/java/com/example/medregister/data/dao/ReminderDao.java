package com.example.medregister.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medregister.models.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminders_table")
    void deleteAllReminders();

    @Query("SELECT * FROM reminders_table ORDER BY date ASC")
    LiveData<List<Reminder>> getAllReminders();
}
