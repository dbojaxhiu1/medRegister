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
public class RegisterPillsActivityTest {

    @Rule
    public ActivityTestRule<RegisterPillsActivity> registerPillsActivityActivityTestRule =
            new ActivityTestRule<RegisterPillsActivity>(RegisterPillsActivity.class);

    private RegisterPillsActivity registerPillsActivity = null;

    Instrumentation.ActivityMonitor monitorAddEditPillActivity =
            getInstrumentation().addMonitor(AddEditPillActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        registerPillsActivity = registerPillsActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchAddPillActivity() {
        assertNotNull(registerPillsActivity.findViewById(R.id.fob_add));
        onView(withId(R.id.fob_add)).perform(click());

        Activity addPillActivity = getInstrumentation().waitForMonitorWithTimeout(monitorAddEditPillActivity, 5000);
        assertNotNull(addPillActivity);
        addPillActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        registerPillsActivity = null;
    }
}