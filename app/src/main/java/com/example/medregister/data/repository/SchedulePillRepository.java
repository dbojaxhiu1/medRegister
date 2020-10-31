package com.example.medregister.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.medregister.data.SchedulePillDatabase;
import com.example.medregister.data.dao.SchedulePillDao;
import com.example.medregister.models.SchedulePill;

import java.util.List;

public class SchedulePillRepository {

    private SchedulePillDao schedulePillDao;
    private LiveData<List<SchedulePill>> allScheduledPills;

    public SchedulePillRepository(Application application) {
        SchedulePillDatabase database = SchedulePillDatabase.getInstance(application);
        schedulePillDao = database.schedulePillDao();
        allScheduledPills = schedulePillDao.getAllScheduledPills();
    }

    public void insert(SchedulePill schedulePill) {
        new InsertScheduledPillsAsyncTask(schedulePillDao).execute(schedulePill);
    }

    public void update(SchedulePill schedulePill) {
        new UpdateScheduledPillsAsyncTask(schedulePillDao).execute(schedulePill);
    }

    public void delete(SchedulePill schedulePill) {
        new DeleteScheduledPillsAsyncTask(schedulePillDao).execute(schedulePill);
    }

    public void deleteAllScheduledPills() {
        new DeleteAllScheduledPillsAsyncTask(schedulePillDao).execute();
    }

    public LiveData<List<SchedulePill>> getAllScheduledPills() {
        return allScheduledPills;
    }

    private static class InsertScheduledPillsAsyncTask extends AsyncTask<SchedulePill, Void, Void> {
        private SchedulePillDao schedulePillDao;

        private InsertScheduledPillsAsyncTask(SchedulePillDao schedulePillDao) {
            this.schedulePillDao = schedulePillDao;
        }

        @Override
        protected Void doInBackground(SchedulePill... schedulePills) {
            schedulePillDao.insert(schedulePills[0]);
            return null;
        }
    }

    private static class UpdateScheduledPillsAsyncTask extends AsyncTask<SchedulePill, Void, Void> {
        private SchedulePillDao schedulePillDao;

        private UpdateScheduledPillsAsyncTask(SchedulePillDao schedulePillDao) {
            this.schedulePillDao = schedulePillDao;
        }

        @Override
        protected Void doInBackground(SchedulePill... schedulePills) {
            schedulePillDao.update(schedulePills[0]);
            return null;
        }
    }

    private static class DeleteScheduledPillsAsyncTask extends AsyncTask<SchedulePill, Void, Void> {
        private SchedulePillDao schedulePillDao;

        private DeleteScheduledPillsAsyncTask(SchedulePillDao schedulePillDao) {
            this.schedulePillDao = schedulePillDao;
        }

        @Override
        protected Void doInBackground(SchedulePill... schedulePills) {
            schedulePillDao.delete(schedulePills[0]);
            return null;
        }
    }

    private static class DeleteAllScheduledPillsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SchedulePillDao schedulePillDao;

        private DeleteAllScheduledPillsAsyncTask(SchedulePillDao schedulePillDao) {
            this.schedulePillDao = schedulePillDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            schedulePillDao.deleteAllScheduledPills();
            return null;
        }
    }
}
