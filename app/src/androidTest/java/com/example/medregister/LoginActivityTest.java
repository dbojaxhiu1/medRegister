package com.example.medregister;


import android.app.Activity;
import android.app.Instrumentation;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.typeText;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@LargeTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity loginActivity = null;
    private String email = "diar.bojaxhi@gmail.com";
    private String password = "Diarb123@";

    Instrumentation.ActivityMonitor monitorSignedInActivity =
            getInstrumentation().addMonitor(SignedInActivity.class.getName(),
                    null,false);
    @Before
    public void setUp() throws Exception{
        loginActivity =  mActivityTestRule.getActivity();
    }
    @Test
    public void loginActivityTest() {

        assertNotNull(loginActivity.findViewById(R.id.email));
        assertNotNull(loginActivity.findViewById(R.id.password));
        assertNotNull(loginActivity.findViewById(R.id.email_sign_in_button));
        onView(withId(R.id.email)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();

        onView(withId(R.id.email_sign_in_button)).perform(click());

        Activity signedInActivity = getInstrumentation().waitForMonitorWithTimeout(monitorSignedInActivity,5000);
        assertNotNull(signedInActivity);
        signedInActivity.finish();
    }

    @After
    public void tearDown() throws Exception{
        loginActivity = null;
    }
}