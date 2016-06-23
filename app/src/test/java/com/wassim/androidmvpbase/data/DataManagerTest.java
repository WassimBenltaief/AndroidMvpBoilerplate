package com.wassim.androidmvpbase.data;

import com.wassim.androidmvpbase.data.local.database.DatabaseHelper;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by beltaief on 2/4/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock ApiService mApiServiceMock;
    @Mock DatabaseHelper mDatabaseHelperMock;
    @Mock PreferencesHelper mPreferencesHelperMock;
    @Mock RxEventBusHelper mEventPosterMock;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mApiServiceMock, mPreferencesHelperMock,
                mDatabaseHelperMock, mEventPosterMock);
    }

    /*@Test
    public void getAndSaveMoviesTest() {
        List<Movie> movies = TestDataFactory.makeMovies();

        when(mApiServiceMock.getMovies())
                .thenReturn(Observable.just(movies));

        when(mDatabaseHelperMock.saveMovies(movies))
                .thenReturn(Observable.from(movies));

        TestSubscriber<Movie> result = new TestSubscriber<>();
        mDataManager.getApiService().getMovies().subscribe(result);

        //result.assertNoErrors();
        result.assertReceivedOnNext(movies);
    }*/
}
