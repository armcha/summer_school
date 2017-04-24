package com.luseen.yandexsummerschool;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.luseen.yandexsummerschool.ui.activity.root.RootActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Chatikyan on 24.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class RootActivityTest {

    @Rule
    public ActivityTestRule<RootActivity> activityTestRule = new ActivityTestRule<>(RootActivity.class);

    @Test
    public void testBottomNavigation() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        // Click on the button
        onView(withId(R.id.bottom_navigation)).perform(click());
    }
}
