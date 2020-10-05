package com.example.medregister.databases;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainNotesDao {
    @Insert(onConflict = REPLACE)
    void insert(NotesData notesData);

    @Delete
    void delete(NotesData notesData);

    //delete all queries
    @Delete
    void reset(List<NotesData> notesData);

    @Query("UPDATE notes_table SET text = :sText WHERE id =:sid")
    void update(int sid, String sText);

    @Query("SELECT * FROM notes_table")
    List<NotesData> getAll();
}
