package com.example.medregister.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PillDao {

    @Insert
    void insert(Pill pill);

    @Update
    void update(Pill pill);

    @Delete
    void delete(Pill pill);

    @Query("DELETE FROM pill_table")
    void deleteAllPills();

    @Query("SELECT * FROM pill_table ORDER BY usage DESC")
    LiveData<List<Pill>> getAllPills();
}
