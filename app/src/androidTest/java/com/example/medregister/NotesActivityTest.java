package com.example.medregister;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class NotesActivityTest {

    @Rule
    public ActivityTestRule<NotesActivity> notesActivityActivityTestRule =
            new ActivityTestRule<NotesActivity>(NotesActivity.class);

    private NotesActivity notesActivity = null;

    private String note = "testing note activity";

    @Before
    public void setUp() throws Exception {
        notesActivity = notesActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchAddNoteActivity() {
        assertNotNull(notesActivity.findViewById(R.id.edit_text_note));
        onView(withId(R.id.edit_text_note)).perform(typeText(note));
        closeSoftKeyboard();

        assertNotNull(notesActivity.findViewById(R.id.button_add_note));
        onView(withId(R.id.button_add_note)).perform(click());

    }


    @After
    public void tearDown() throws Exception {
        notesActivity = null;
    }
}