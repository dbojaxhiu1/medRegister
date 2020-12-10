package com.example.medregister.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.medregister.data.dao.SchedulePillDao;
import com.example.medregister.models.SchedulePill;

@Database(entities = {SchedulePill.class}, version = 2, exportSchema = false)
public abstract class SchedulePillDatabase extends RoomDatabase {

    private static SchedulePillDatabase database;
    private static String DATABASE_NAME = "database";

    public abstract SchedulePillDao schedulePillDao();

    public static synchronized SchedulePillDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    SchedulePillDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(database).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private SchedulePillDao schedulePillDao;

        private PopulateDBAsyncTask(SchedulePillDatabase db){
            schedulePillDao = db.schedulePillDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            schedulePillDao.insert(new SchedulePill("Name 2","11:11","2"));
            schedulePillDao.insert(new SchedulePill("Name 2","11:11","2"));
            schedulePillDao.insert(new SchedulePill("Name 2","11:11","2"));
            return null;
        }
    }
}
