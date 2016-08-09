package com.wassim.androidmvpbase.ui.views.main;

import com.wassim.androidmvpbase.util.RxEventBusHelper;
import com.wassim.androidmvpbase.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvp.View mMockMainMvp;

    @Mock MainProvider mMockProvider;

    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        RxEventBusHelper rxEventBusHelper = spy(RxEventBusHelper.class);
        mMainPresenter = new MainPresenter(mMockProvider);
        mMainPresenter.attachView(mMockMainMvp);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadMoviesTest() {
        /*List<MovieModel> movies = TestDataFactory.makeMovies();

        doReturn(Observable.just(movies))
                .when(mMockProvider)
                .getMovies();

        mMainPresenter.loadMovies();
        List<Movie> cachedandNewList = new ArrayList<>();
        cachedandNewList.addAll(movies);


        verify(mMockMainMvp, times(1)).showMovies(movies);
        verify(mMockMainMvp, never()).showEmpty();
        verify(mMockMainMvp, never()).showError();*/
    }
}
