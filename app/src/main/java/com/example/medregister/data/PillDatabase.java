package com.example.medregister.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.medregister.data.dao.PillDao;
import com.example.medregister.models.Pill;

@Database(entities = {Pill.class}, version = 11, exportSchema = false)
public abstract class PillDatabase extends RoomDatabase {

    private static PillDatabase database;
    private static String DATABASE_NAME = "database";

    public abstract PillDao pillDao();

    /*
    static Migration migration = new Migration(7,10){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //create new table
            database.execSQL("CREATE TABLE 'pills_table' (instruction TEXT, name TEXT, packageContains INTEGER, usage INTEGER, creationDate TEXT," +
                    " id INTEGER PRIMARY KEY NOT NULL)");
            //copy the data
            database.execSQL("INSERT INTO 'pills_table'(instruction,name,packageContains,usage,creationDate,id) SELECT instruction,name,packageContains,usage,creationDate,id FROM 'pill_table'");
            //remove the old table
            database.execSQL("DROP TABLE 'pill_table'");
            //change the table name to the correct one
            database.execSQL("ALTER TABLE 'pills_table' RENAME TO 'pill_table'");

        }
    };*/

    public static synchronized PillDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    PillDatabase.class, DATABASE_NAME)
                     .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
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
        private PillDao pillDao;

        private PopulateDBAsyncTask(PillDatabase db) {
            pillDao = db.pillDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //pillDao.insert(new Pill("Name 1", "Instruction 1", 1, 6,""));
            //pillDao.insert(new Pill("Name 2", "Instruction 2", 2, 7));
            //pillDao.insert(new Pill("Name 3", "Instruction 3", 3, 8));
            return null;
        }
    }
}
