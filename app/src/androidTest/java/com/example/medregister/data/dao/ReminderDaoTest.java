package com.example.medregister.data.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.medregister.LiveDataUtilTest;
import com.example.medregister.data.ReminderDatabase;
import com.example.medregister.models.Reminder;

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
public class ReminderDaoTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private ReminderDatabase database;
    private ReminderDao reminderDao;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ReminderDatabase.class
        ).allowMainThreadQueries().build();

        reminderDao = database.reminderDao();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insertReminder() throws InterruptedException {
        Reminder reminder = new Reminder("text", "12/12/2020");
        database.reminderDao().insert(reminder);

        LiveDataUtilTest<List<Reminder>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Reminder> insertedReminders = listLiveDataUtilTest.getOrAwaitValue(reminderDao.getAllReminders());

        assertNotNull(insertedReminders);
        assertEquals(reminder.getText(), insertedReminders.get(0).getText());
        assertEquals(reminder.getDate(), insertedReminders.get(0).getDate());
    }

    @Test
    public void deleteReminder() throws InterruptedException {
        Reminder reminder = new Reminder("text", "12/12/2020");
        database.reminderDao().insert(reminder);

        LiveDataUtilTest<List<Reminder>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Reminder> insertedReminders = listLiveDataUtilTest.getOrAwaitValue(reminderDao.getAllReminders());
        assertNotNull(insertedReminders);
        database.reminderDao().delete(reminder);
    }

    @Test
    public void updateReminder() throws InterruptedException {
        Reminder reminder = new Reminder("text", "12/12/2020");
        database.reminderDao().insert(reminder);

        LiveDataUtilTest<List<Reminder>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Reminder> insertedReminders = listLiveDataUtilTest.getOrAwaitValue(reminderDao.getAllReminders());
        assertNotNull(insertedReminders);
        database.reminderDao().update(reminder);
    }

    @Test
    public void deleteAllReminders() throws InterruptedException {
        Reminder reminder = new Reminder("text", "12/12/2020");
        Reminder reminder_1 = new Reminder("text", "12/12/2020");

        database.reminderDao().insert(reminder);
        database.reminderDao().insert(reminder_1);

        LiveDataUtilTest<List<Reminder>> listLiveDataUtilTest = new LiveDataUtilTest<>();
        List<Reminder> insertedReminders = listLiveDataUtilTest.getOrAwaitValue(reminderDao.getAllReminders());
        assertNotNull(insertedReminders);
        database.reminderDao().deleteAllReminders();
    }
}