package com.wassim.androidmvpbase.data;

import com.wassim.androidmvpbase.data.local.database.DatabaseHelper;
import com.wassim.androidmvpbase.data.local.preferences.PreferencesHelper;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.data.remote.ApiService;
import com.wassim.androidmvpbase.test.common.TestDataFactory;
import com.wassim.androidmvpbase.util.RxEventBusHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by beltaief on 2/4/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    ApiService mApiServiceMock;

    @Mock
    DatabaseHelper mDatabaseHelperMock;

    @Mock
    PreferencesHelper mPreferencesHelperMock;

    @Mock
    RxEventBusHelper mEventPosterMock;

    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mApiServiceMock, mPreferencesHelperMock,
                mDatabaseHelperMock, mEventPosterMock);
    }

    @Test
    public void getAndSaveMoviesTest() {
        List<Movie> movies = TestDataFactory.makeMovies();
        doReturn(Observable.just(movies))
                .when(mApiServiceMock)
                .getMovies();

        TestSubscriber<Movie> result = new TestSubscriber<>();
        mDataManager.getAndSaveMovies().subscribe(result);

        result.assertNoErrors();
        result.assertReceivedOnNext(movies);
    }
}
