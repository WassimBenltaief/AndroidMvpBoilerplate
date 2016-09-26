package com.wassim.androidmvpbase.ui.views.single;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wassim.androidmvpbase.R;
import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleMovieActivity extends BaseActivity implements SingleMovieMvp.View {

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    @Inject
    SingleMoviePresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.movie_genre)
    TextView mMovieGenre;
    @BindView(R.id.movie_rating)
    TextView mMovieRating;
    @BindView(R.id.movie_synopsis)
    TextView mMovieSynopsis;
    @BindView(R.id.movie_year)
    TextView mMovieYear;

    private long mMovieId;
    private Movie mMovie;
    private boolean inFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_single_movie);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        mMovieId = bundle.getLong("movie");
        mPresenter.getMovie(mMovieId);

        ViewCompat.setTransitionName(mImage, VIEW_NAME_HEADER_IMAGE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        mMovie = movie;
        addTransitionListener();
        loadFullSizeImage();
        mCollapsingToolbar.setTitle(mMovie.title());
        mMovieGenre.setText(mMovie.genre());
        mMovieYear.setText(mMovie.releaseYear() + "");
        mMovieRating.setText(mMovie.rating() + "");
        mMovieSynopsis.setText(mMovie.synopsis());

        favoritesChecked(movie.checked() == 1, false);

    }

    private void loadFullSizeImage() {
        Picasso.with(this)
                .load(mMovie.image())
                .noFade()
                .noPlaceholder()
                .into(mImage);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage();

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
        }

    }


    @Override
    public void favoritesChecked(boolean state, boolean btnClicked) {
        if (state) {
            mFab.setImageResource(R.drawable.star_on);
            if (btnClicked)
                Toast.makeText(this, R.string.movie_added_fav, Toast.LENGTH_SHORT).show();
        } else {
            mFab.setImageResource(R.drawable.star_off);
            if (btnClicked)
                Toast.makeText(this, R.string.movie_removed_fav, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab)
    public void updateMovieInFavorites() {
        mPresenter.modifyFavorites(mMovie);
    }
}
