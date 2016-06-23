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

import com.wassim.androidmvpbase.R;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleMovieActivity extends BaseActivity implements SingleMovieMvp.View {

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    @Inject
    SingleMoviePresenter mPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.movie_genre)
    TextView mMovieGenre;
    @Bind(R.id.movie_rating)
    TextView mMovieRating;
    @Bind(R.id.movie_synopsis)
    TextView mMovieSynopsis;
    @Bind(R.id.movie_year)
    TextView mMovieYear;


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
        mMovie = bundle.getParcelable("movie");


        ViewCompat.setTransitionName(mImage, VIEW_NAME_HEADER_IMAGE);
        mPresenter.checkFavorites(mMovie);
        loadMovie();
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

    private void loadMovie() {
        addTransitionListener();
        loadFullSizeImage();
        mCollapsingToolbar.setTitle(mMovie.getTitle());
        mMovieGenre.setText(mMovie.getGenre());
        mMovieYear.setText(mMovie.getReleaseYear() + "");
        mMovieRating.setText(mMovie.getRating() + "");
        mMovieSynopsis.setText(mMovie.getSynopsis());

    }

    private void loadFullSizeImage() {
        Picasso.with(this)
                .load(mMovie.getImage())
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
