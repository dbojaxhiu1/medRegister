package com.example.medregister.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainPillsDao {
    @Insert(onConflict = REPLACE)
    void insert(PillsData pillsData);

    @Delete
    void delete(PillsData pillsData);

    //delete all queries, not useful for now
    @Delete
    void reset(List<PillsData> pillsData);

    @Query("UPDATE pills_table SET pillName = :sPillName AND pillInstruction = :sPillInstruction " +
            "AND numberOfPills= :sNumberOfPills AND dailyUsage = :sDailyUsage WHERE id =:sid")
    void update(int sid, String sPillName, String sPillInstruction, String sNumberOfPills, String sDailyUsage);

    @Query("SELECT * FROM pills_table")
    List<PillsData> getAll();
}