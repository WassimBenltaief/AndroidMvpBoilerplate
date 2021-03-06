package com.wassim.androidmvpbase.ui.views.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wassim.androidmvpbase.R;
import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.ui.base.BaseActivity;
import com.wassim.androidmvpbase.ui.views.single.SingleMovieActivity;
import com.wassim.androidmvpbase.util.DialogFactory;
import com.wassim.androidmvpbase.util.DividerItemDecoration;
import com.wassim.androidmvpbase.util.RecyclerViewClickListener;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements MainMvp.View, RecyclerViewClickListener {

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeContainer;

    private MoviesAdapter mAdapter;
    private List<Movie> mMovies;
    private static ProgressDialog mProgressDialog;

    private Unbinder unbinder;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        mPresenter.attachView(this);
        mPresenter.attachToProvider();

        setSupportActionBar(mToolbar);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));

        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL)
        );
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter.loadMovies();


        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadMovies();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachFromProvider();
        mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        hideProgress();
        mMovies = movies;
        mAdapter.setMovies(mMovies);
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        hideProgress();
        mAdapter.setMovies(Collections.emptyList());
        Toast.makeText(this, R.string.empty_movies, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_movies))
                .show();
    }


    @Override
    public void recyclerViewListClicked(View v, int position, int id) {
        Movie movie = mMovies.get(position);
        Intent intent = new Intent(this, SingleMovieActivity.class);
        intent.putExtra("movie", movie.id());

        ActivityOptionsCompat activityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        new Pair<View, String>(v.findViewById(R.id.movie_image),
                                SingleMovieActivity.VIEW_NAME_HEADER_IMAGE));
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());

    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

}
