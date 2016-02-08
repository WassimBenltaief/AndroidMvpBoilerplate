package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.test.common.TestDataFactory;
import com.wassim.androidmvpbase.util.RxEventBusHelper;
import com.wassim.androidmvpbase.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMockMainMvpView;

    @Mock DataManager mMockDataManager;

    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        RxEventBusHelper rxEventBusHelper = spy(RxEventBusHelper.class);
        doReturn(rxEventBusHelper)
                .when(mMockDataManager)
                .getEventPoster();
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadMoviesTest() {
        List<Movie> movies = TestDataFactory.makeMovies();

        doReturn(Observable.just(movies))
                .when(mMockDataManager)
                .getCachedMovies();

        mMainPresenter.loadCashedMovies();
        List<Movie> cachedandNewList = new ArrayList<>();
        cachedandNewList.addAll(movies);


        verify(mMockMainMvpView, times(1)).showMovies(movies);
        verify(mMockMainMvpView, never()).showEmpty();
        verify(mMockMainMvpView, never()).showError();
    }
}
