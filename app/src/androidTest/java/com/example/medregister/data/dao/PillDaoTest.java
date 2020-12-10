package com.example.medregister.data.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.medregister.LiveDataUtilTest;
import com.example.medregister.data.PillDatabase;
import com.example.medregister.models.Pill;

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
public class PillDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PillDatabase database;
    private PillDao pillDao;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                PillDatabase.class
        ).allowMainThreadQueries().build();

        pillDao = database.pillDao();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insertPill() throws InterruptedException {
        Pill pill = new Pill("name", "instruction", 1, 20);
        database.pillDao().insert(pill);
        LiveDataUtilTest<List<Pill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Pill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(pillDao.getAllPills());

        assertNotNull(insertedPills);
        assertEquals(pill.getName(), insertedPills.get(0).getName());
        assertEquals(pill.getInstruction(), insertedPills.get(0).getInstruction());
        assertEquals(pill.getUsage(), insertedPills.get(0).getUsage());
        assertEquals(pill.getPackageContains(), insertedPills.get(0).getPackageContains());
    }

    @Test
    public void deletePill() throws InterruptedException {
        Pill pill = new Pill("name", "instruction", 1, 20);
        database.pillDao().insert(pill);

        LiveDataUtilTest<List<Pill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Pill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(pillDao.getAllPills());
        assertNotNull(insertedPills);
        database.pillDao().delete(pill);
    }

    @Test
    public void updatePill() throws InterruptedException {
        Pill pill = new Pill("name", "instruction", 1, 20);
        database.pillDao().insert(pill);

        LiveDataUtilTest<List<Pill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Pill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(pillDao.getAllPills());
        assertNotNull(insertedPills);
        database.pillDao().update(pill);
    }

    @Test
    public void deleteAllPills() throws InterruptedException {
        Pill pill = new Pill("name", "instruction", 1, 20);
        Pill pill_1 = new Pill("name", "instruction", 1, 20);

        database.pillDao().insert(pill);
        database.pillDao().insert(pill_1);

        LiveDataUtilTest<List<Pill>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Pill> insertedPills = listLiveDataUtilTest.getOrAwaitValue(pillDao.getAllPills());
        assertNotNull(insertedPills);
        database.pillDao().deleteAllPills();
    }
}