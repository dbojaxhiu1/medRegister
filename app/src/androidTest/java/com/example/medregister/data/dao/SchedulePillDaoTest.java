package com.example.medregister.data.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.medregister.LiveDataUtilTest;
import com.example.medregister.data.SchedulePillDatabase;
import com.example.medregister.models.SchedulePill;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SchedulePillDaoTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private SchedulePillDatabase database;
    private SchedulePillDao schedulePillDao;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                SchedulePillDatabase.class
        ).allowMainThreadQueries().build();

        schedulePillDao = database.schedulePillDao();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insert() throws InterruptedException {
        SchedulePill pill = new SchedulePill("name", "12:12", "500 mg");
        database.schedulePillDao().insert(pill);
        LiveDataUtilTest<List<SchedulePill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<SchedulePill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(schedulePillDao.getAllScheduledPills());

        assertNotNull(insertedPills);
        assertEquals(pill.getPillName(), insertedPills.get(0).getPillName());
        assertEquals(pill.getTime(), insertedPills.get(0).getTime());
        assertEquals(pill.getDose(), insertedPills.get(0).getDose());
    }

    @Test
    public void update() throws InterruptedException {
        SchedulePill pill = new SchedulePill("name", "12:12", "500 mg");
        database.schedulePillDao().insert(pill);
        LiveDataUtilTest<List<SchedulePill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<SchedulePill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(schedulePillDao.getAllScheduledPills());
        assertNotNull(insertedPills);
        database.schedulePillDao().update(pill);
    }

    @Test
    public void delete() throws InterruptedException {
        SchedulePill pill = new SchedulePill("name", "12:12", "500 mg");
        database.schedulePillDao().insert(pill);
        LiveDataUtilTest<List<SchedulePill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<SchedulePill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(schedulePillDao.getAllScheduledPills());
        assertNotNull(insertedPills);
        database.schedulePillDao().delete(pill);
    }

    @Test
    public void deleteAllScheduledPills() throws InterruptedException {
        SchedulePill pill = new SchedulePill("name", "12:12", "500 mg");
        SchedulePill pill_1 = new SchedulePill("name", "12:12", "500 mg");
        database.schedulePillDao().insert(pill);
        database.schedulePillDao().insert(pill_1);
        LiveDataUtilTest<List<SchedulePill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<SchedulePill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(schedulePillDao.getAllScheduledPills());
        assertNotNull(insertedPills);
        database.schedulePillDao().deleteAllScheduledPills();
    }
}