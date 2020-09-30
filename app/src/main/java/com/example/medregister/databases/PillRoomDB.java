package com.example.medregister.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Database entities
@Database(entities = {PillsData.class}, version = 1, exportSchema = false)
public abstract class PillRoomDB extends RoomDatabase {
    //database instances
    private static PillRoomDB database;
    //database name
    private static String DATABASE_NAME = "database";

    public synchronized static PillRoomDB getInstance(Context context) {

        if (database == null) {
            //when the database is null, initialize database
            database = Room.databaseBuilder(context.getApplicationContext()
                    , PillRoomDB.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }
        //return database
        return database;
    }

    //create DAO
    public abstract MainPillsDao mainPillsDao();
}