package com.example.medregister;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HealthyTipsActivityTest {

    @Rule
    public ActivityTestRule<HealthyTipsActivity> healthyTipsActivityActivityTestRule =
            new ActivityTestRule<HealthyTipsActivity>(HealthyTipsActivity.class);

    private HealthyTipsActivity healthyTipsActivity = null;

    @Before
    public void setUp() throws Exception {
        healthyTipsActivity = healthyTipsActivityActivityTestRule.getActivity();
    }

    @Test
    public void swipeRightView() {

        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.viewPager)).perform(swipeLeft());

        onView(withId(R.id.viewPager)).perform(swipeRight());
        onView(withId(R.id.viewPager)).perform(swipeRight());
        onView(withId(R.id.viewPager)).perform(swipeRight());
        onView(withId(R.id.viewPager)).perform(swipeRight());

    }

    @After
    public void tearDown() throws Exception {
        healthyTipsActivity = null;
    }
}