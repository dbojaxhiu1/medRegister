package com.example.medregister.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Database entities
@Database(entities = {NotesData.class},version=1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    //database instances
    private static NoteDatabase database;
    private static String DATABASE_NAME = "database";
    public synchronized static NoteDatabase getInstance(Context context){

        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
                      , NoteDatabase.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build();
        }
        return database;
    }

    public abstract MainNotesDao mainNotesDao();
}
