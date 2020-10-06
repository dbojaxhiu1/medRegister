package com.example.medregister.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.medregister.data.dao.ReminderDao;
import com.example.medregister.models.Reminder;

@Database(entities = {Reminder.class}, version = 7, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ReminderDatabase extends RoomDatabase {

    private static ReminderDatabase database;
    private static String DATABASE_NAME = "database";

    public abstract ReminderDao reminderDao();

    public static synchronized ReminderDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    ReminderDatabase.class, DATABASE_NAME).allowMainThreadQueries()
                   // .addCallback(roomCallback)
                    .build();
        }
        return database;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ReminderDao reminderDao;

        private PopulateDBAsyncTask(ReminderDatabase db) {
            reminderDao = db.reminderDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            reminderDao.insert(new Reminder("New reminder 1", "2000/02/02"));
            //reminderDao.insert(new Reminder("New reminder 2", new Date(2010, 1, 3)));
            //reminderDao.insert(new Reminder("New reminder 3", new Date(2020, 02, 03)));
            return null;
        }
    }
}

