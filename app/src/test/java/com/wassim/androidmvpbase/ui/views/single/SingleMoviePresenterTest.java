package com.wassim.androidmvpbase.ui.views.single;

import com.wassim.androidmvpbase.data.DataManager;
import com.wassim.androidmvpbase.data.model.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleMoviePresenterTest {

    @Mock
    SingleMovieMvpView mSingleMovieMvpView;

    @Mock
    DataManager mMockDataManager;

    private SingleMoviePresenter mSingleMoviePresenter;

    /*@Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();*/

    @Before
    public void setUp() {
        mSingleMoviePresenter = new SingleMoviePresenter(mMockDataManager);
        mSingleMoviePresenter.attachView(mSingleMovieMvpView);
    }

    @After
    public void tearDown() {
        mSingleMoviePresenter.detachView();
    }

    @Test
    public void checkFavoritesTest() {

        Movie movie = new Movie();
        movie.setId(1);

        when(mMockDataManager.verifyMovie(movie.getId())).thenReturn(true);
        mSingleMoviePresenter.checkFavorites(movie);
        verify(mSingleMovieMvpView).favoritesChecked(true, false);

        when(mMockDataManager.verifyMovie(movie.getId())).thenReturn(false);
        mSingleMoviePresenter.checkFavorites(movie);
        verify(mSingleMovieMvpView).favoritesChecked(true, false);
    }

    @Test
    public void modifyFavoritesTest() {

        Movie movie = new Movie();
        movie.setId(1);

        when(mMockDataManager.verifyMovie(movie.getId())).thenReturn(true);
        mSingleMoviePresenter.modifyFavorites(movie);

        verify(mMockDataManager).removeMovie(movie);
        verify(mSingleMovieMvpView).favoritesChecked(false, true);

        verify(mMockDataManager, never()).addMovie(movie);
        verify(mSingleMovieMvpView, never()).favoritesChecked(true, false);
    }

}
