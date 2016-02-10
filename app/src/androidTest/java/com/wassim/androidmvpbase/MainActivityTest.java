package com.wassim.androidmvpbase;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.test.common.TestDataFactory;
import com.wassim.androidmvpbase.test.common.rules.TestComponentRule;
import com.wassim.androidmvpbase.ui.views.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext());
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);


    @Test
    public void shouldbeAbleToLoadMovies(){
        List<Movie> movies = TestDataFactory.makeMovies();
        when(component.getMockDataManager().getMovies())
                .thenReturn(Observable.just(movies));

        main.launchActivity(null);

        int position = 0;
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        String genre = movies.get(0).getGenre();
        onView(withText(genre))
                .check(matches(isDisplayed()));
    }

}