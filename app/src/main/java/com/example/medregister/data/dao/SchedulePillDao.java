package com.example.medregister.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medregister.models.SchedulePill;

import java.util.List;

@Dao
public interface SchedulePillDao {

    @Insert
    void insert(SchedulePill schedulePill);

    @Update
    void update(SchedulePill schedulePill);

    @Delete
    void delete(SchedulePill schedulePill);

    @Query("DELETE FROM schedule_table")
    void deleteAllScheduledPills();

    @Query("SELECT * FROM schedule_table ORDER BY time ASC")
    LiveData<List<SchedulePill>> getAllScheduledPills();
}
