package com.example.medregister.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.medregister.data.dao.NotesDao;
import com.example.medregister.models.Note;

//Database entities
@Database(entities = {Note.class}, version = 5, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    //database instances
    private static NoteDatabase database;
    private static String DATABASE_NAME = "database";

    public synchronized static NoteDatabase getInstance(Context context) {

        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext()
                    , NoteDatabase.class, DATABASE_NAME)
                     .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract NotesDao NoteDao();
}
