package com.example.medregister.data.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.medregister.models.Note;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NotesDao {
    @Insert(onConflict = REPLACE)
    void insert(Note notesData);

    @Delete
    void delete(Note notesData);

    //delete all queries
    @Delete
    void reset(List<Note> notesData);

    @Query("UPDATE notes_table SET text = :sText WHERE id =:sid")
    void update(int sid, String sText);

    @Query("SELECT * FROM notes_table")
    List<Note> getAll();
}
