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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SignedInActivityTest {

    @Rule
    public ActivityTestRule<SignedInActivity> signedInActivityActivityTestRule = new ActivityTestRule<SignedInActivity>(SignedInActivity.class);

    private SignedInActivity signedInActivity = null;

    Instrumentation.ActivityMonitor monitorRegisterPillActivity =
            getInstrumentation().addMonitor(RegisterPillsActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorSchedulePillActivity =
            getInstrumentation().addMonitor(SchedulePillsActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorNotesActivity =
            getInstrumentation().addMonitor(NotesActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorToDoActivity =
            getInstrumentation().addMonitor(ToDoActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorHealthyTipsActivity =
            getInstrumentation().addMonitor(HealthyTipsActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        signedInActivity = signedInActivityActivityTestRule.getActivity();
    }


    @Test
    public void testLaunch() {

        assertNotNull(signedInActivity.findViewById(R.id.imageViewRegisterPill));
        assertNotNull(signedInActivity.findViewById(R.id.imageViewSchedulePill));
        assertNotNull(signedInActivity.findViewById(R.id.imageViewNotes));
        assertNotNull(signedInActivity.findViewById(R.id.imageViewToDo));
        assertNotNull(signedInActivity.findViewById(R.id.imageViewHealthyTips));

        assertNotNull(signedInActivity.findViewById(R.id.textViewRegisterPill));
        assertNotNull(signedInActivity.findViewById(R.id.textViewSchedulePill));
        assertNotNull(signedInActivity.findViewById(R.id.textViewNotes));
        assertNotNull(signedInActivity.findViewById(R.id.textViewToDo));
        assertNotNull(signedInActivity.findViewById(R.id.textViewHealthyTips));
    }

    @Test
    public void testLaunchRegisterPillActivityOnClick() {
        assertNotNull(signedInActivity.findViewById(R.id.registerPillButton));
        onView(withId(R.id.registerPillButton)).perform(click());

        Activity registerPillActivity = getInstrumentation().waitForMonitorWithTimeout(monitorRegisterPillActivity, 5000);
        assertNotNull(registerPillActivity);
        registerPillActivity.finish();
    }

    @Test
    public void testLaunchSchedulePillActivityOnClick() {
        assertNotNull(signedInActivity.findViewById(R.id.schedulePillButton));
        onView(withId(R.id.schedulePillButton)).perform(click());

        Activity schedulePillsActivity = getInstrumentation().waitForMonitorWithTimeout(monitorSchedulePillActivity, 5000);
        assertNotNull(schedulePillsActivity);
        schedulePillsActivity.finish();

    }

    @Test
    public void testLaunchNotesActivityOnClick() {
        assertNotNull(signedInActivity.findViewById(R.id.notesButton));
        onView(withId(R.id.notesButton)).perform(click());

        Activity notesActivity = getInstrumentation().waitForMonitorWithTimeout(monitorNotesActivity, 5000);
        assertNotNull(notesActivity);
        notesActivity.finish();
    }

    @Test
    public void testLaunchToDoActivityOnClick() {
        assertNotNull(signedInActivity.findViewById(R.id.toDoButton));
        onView(withId(R.id.toDoButton)).perform(click());

        Activity toDoActivity = getInstrumentation().waitForMonitorWithTimeout(monitorToDoActivity, 5000);
        assertNotNull(toDoActivity);
        toDoActivity.finish();
    }

    @Test
    public void testLaunchHealthyTipsActivityOnClick() {
        assertNotNull(signedInActivity.findViewById(R.id.healthyTipsButton));
        onView(withId(R.id.healthyTipsButton)).perform(scrollTo(), click());

        Activity healthyTipsActivity = getInstrumentation().waitForMonitorWithTimeout(monitorHealthyTipsActivity, 5000);
        assertNotNull(healthyTipsActivity);
        healthyTipsActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        signedInActivity = null;
    }
}