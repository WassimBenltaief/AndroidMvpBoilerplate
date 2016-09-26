package com.wassim.androidmvpbase;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wassim.androidmvpbase.data.model.RemoteMovie;
import com.wassim.androidmvpbase.test.common.TestDataFactory;
import com.wassim.androidmvpbase.test.common.rules.TestComponentRule;
import com.wassim.androidmvpbase.ui.views.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

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
        List<RemoteMovie> movies = TestDataFactory.makeMovies();
        /*when(component.getMockDataManager().getMovies())
                .thenReturn(Observable.just(movies));
        when(component.getMockDataManager().getEventPoster()).thenReturn(new RxEventBusHelper());
        main.launchActivity(null);

        int position = 0;
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        String genre = movies.get(0).getGenre();
        onView(withText(genre))
                .check(matches(isDisplayed()));*/
    }

}