package com.nlpin.youthsongs;

import android.content.Context;

import com.nlapin.youthsongs.R;
import com.nlapin.youthsongs.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initializing() {
        context = ApplicationProvider.getApplicationContext();
        assertThat(context, notNullValue());
    }

    @Test
    public void testAppOpening() {
        onView(withId(R.id.homeFragment)).perform(ViewActions.swipeDown());
    }
}
