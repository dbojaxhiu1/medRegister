package com.example.medregister;


import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertNotNull;

@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule =
            new ActivityTestRule<>(SignUpActivity.class);

    private SignUpActivity signUpActivity = null;
    private String email = "diari99@gmail.com";
    private String password = "Diarb123@";
    private String confirmPassword = "Diarb123@";

    Instrumentation.ActivityMonitor monitorLoginActivity =
            getInstrumentation().addMonitor(LoginActivity.class.getName(),
                    null,false);

    @Before
    public void setUp() throws Exception {
        signUpActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void signUpActivityTest() {
        assertNotNull(signUpActivity.findViewById(R.id.input_email));
        assertNotNull(signUpActivity.findViewById(R.id.input_password));
        assertNotNull(signUpActivity.findViewById(R.id.input_confirm_password));
        assertNotNull(signUpActivity.findViewById(R.id.email_register));

        onView(withId(R.id.input_email)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.input_password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.input_confirm_password)).perform(typeText(confirmPassword));
        closeSoftKeyboard();

        onView(withId(R.id.email_register)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitorLoginActivity,5000);
        assertNotNull(loginActivity);
        loginActivity.finish();

    }


    @After
    public void tearDown() throws Exception {
        signUpActivity = null;
    }

}