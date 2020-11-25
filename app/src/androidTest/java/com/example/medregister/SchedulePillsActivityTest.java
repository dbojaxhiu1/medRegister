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
public class SchedulePillsActivityTest {

    @Rule
    public ActivityTestRule<SchedulePillsActivity> schedulePillsActivityActivityTestRule =
            new ActivityTestRule<SchedulePillsActivity>(SchedulePillsActivity.class);

    private SchedulePillsActivity schedulePillsActivity = null;

    Instrumentation.ActivityMonitor monitorAddSchedulePillActivity =
            getInstrumentation().addMonitor(AddEditSchedulePillsActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        schedulePillsActivity = schedulePillsActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchAddSchedulePillActivity() {
        assertNotNull(schedulePillsActivity.findViewById(R.id.fob_schedule_pill));
        onView(withId(R.id.fob_schedule_pill)).perform(click());

        Activity addSchedulePillActivity = getInstrumentation().waitForMonitorWithTimeout(monitorAddSchedulePillActivity, 5000);
        assertNotNull(addSchedulePillActivity);
        addSchedulePillActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
        schedulePillsActivity = null;
    }
}