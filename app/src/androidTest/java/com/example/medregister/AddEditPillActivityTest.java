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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AddEditPillActivityTest {

    @Rule
    public ActivityTestRule<AddEditPillActivity> addEditPillActivityActivityTestRule =
            new ActivityTestRule<AddEditPillActivity>(AddEditPillActivity.class);

    private AddEditPillActivity addEditPillActivity = null;
    private String pillName = "Paracetamol";
    private String pillInstruction = "Every morning";
    private int dailyUsage = 1;
    private int packageContains = 50;

    @Before
    public void setUp() throws Exception {
        addEditPillActivity = addEditPillActivityActivityTestRule.getActivity();
    }

    @Test
    public void addPillTest() {
        assertNotNull(addEditPillActivity.findViewById(R.id.edit_pill_name));
        assertNotNull(addEditPillActivity.findViewById(R.id.edit_pill_instruction));
        assertNotNull(addEditPillActivity.findViewById(R.id.number_picker_usage));
        assertNotNull(addEditPillActivity.findViewById(R.id.number_picker_number_in_package));

        onView(withId(R.id.edit_pill_name)).perform(typeText(pillName));
        closeSoftKeyboard();
        onView(withId(R.id.edit_pill_instruction)).perform(typeText(pillInstruction));
        closeSoftKeyboard();

        //openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //onView(withId(R.id.save_pill)).perform(click());


        //assertThat(Iterables.getOnlyElement(Intents.getIntents())).hasComponentClass(
          //      RegisterPillsActivity.class);
        //onView(withId(R.id.number_picker_usage)).perform(typeText(String.valueOf(dailyUsage)));
        //onView(withId(R.id.number_picker_number_in_package)).perform(typeText(String.valueOf(packageContains)));

    }

    @After
    public void tearDown() throws Exception {
        addEditPillActivity = null;
    }
}