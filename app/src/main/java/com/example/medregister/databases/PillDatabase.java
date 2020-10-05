package com.example.medregister.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pill.class}, version = 1,exportSchema = false)
public abstract class PillDatabase extends RoomDatabase {

    private static PillDatabase instance;

    public abstract PillDao pillDao();

    public static synchronized PillDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PillDatabase.class, "pill_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private PillDao pillDao;

        private PopulateDBAsyncTask(PillDatabase db) {
            pillDao = db.pillDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pillDao.insert(new Pill("Name 1", "Instruction 1", 1,6));
            pillDao.insert(new Pill("Name 2", "Instruction 2", 2,7));
            pillDao.insert(new Pill("Name 3", "Instruction 3", 3,8));
            return null;
        }
    }
}
