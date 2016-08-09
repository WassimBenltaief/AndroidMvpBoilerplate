package com.wassim.androidmvpbase.ui.views.single;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SingleMoviePresenterTest {

    @Mock
    SingleMovieMvp.View mSingleMovieMvp;

    @Mock
    SingleMovieProvider mMockProvider;

    private SingleMoviePresenter mSingleMoviePresenter;

    /*@Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();*/

    @Before
    public void setUp() {
        mSingleMoviePresenter = new SingleMoviePresenter(mMockProvider);
        mSingleMoviePresenter.attachView(mSingleMovieMvp);
    }

    @After
    public void tearDown() {
        mSingleMoviePresenter.detachView();
    }

    @Test
    public void checkFavoritesTest() {

        /*Movie movie = new Movie();
        movie.setId(1);

        when(mMockProvider.verifyMovie(movie.getId())).thenReturn(true);
        mSingleMoviePresenter.checkFavorites(movie);
        verify(mSingleMovieMvp).favoritesChecked(true, false);

        when(mMockProvider.verifyMovie(movie.getId())).thenReturn(false);
        mSingleMoviePresenter.checkFavorites(movie);
        verify(mSingleMovieMvp).favoritesChecked(true, false);*/
    }

    @Test
    public void modifyFavoritesTest() {

        /*Movie movie = new Movie();
        movie.setId(1);

        when(mMockProvider.verifyMovie(movie.getId())).thenReturn(true);
        mSingleMoviePresenter.modifyFavorites(movie);

        verify(mMockProvider).removeMovie(movie);
        verify(mSingleMovieMvp).favoritesChecked(false, true);

        verify(mMockProvider, never()).addMovie(movie);
        verify(mSingleMovieMvp, never()).favoritesChecked(true, false);*/
    }

}
