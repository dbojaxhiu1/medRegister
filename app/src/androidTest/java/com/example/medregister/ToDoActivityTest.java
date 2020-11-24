package com.example.medregister;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ToDoActivityTest {

    @Rule
    public ActivityTestRule<ToDoActivity> toDoActivityActivityTestRule =
            new ActivityTestRule<ToDoActivity>(ToDoActivity.class);

    private ToDoActivity toDoActivity = null;

    Instrumentation.ActivityMonitor monitorAddToDoactivity =
            getInstrumentation().addMonitor(AddEditReminderActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        toDoActivity = toDoActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchAddToDoActivity() {
        assertNotNull(toDoActivity.findViewById(R.id.fob_add_reminder));
        onView(withId(R.id.fob_add_reminder)).perform(click());

        Activity addToDo = getInstrumentation().waitForMonitorWithTimeout(monitorAddToDoactivity, 5000);
        assertNotNull(addToDo);
        addToDo.finish();

    }

    @After
    public void tearDown() throws Exception {
        toDoActivity = null;
    }
}