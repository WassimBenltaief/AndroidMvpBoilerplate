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
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BaseActivity;
import com.wassim.androidmvpbase.ui.views.single.SingleMovieActivity;
import com.wassim.androidmvpbase.util.DialogFactory;
import com.wassim.androidmvpbase.util.DividerItemDecoration;
import com.wassim.androidmvpbase.util.RecyclerViewClickListener;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, RecyclerViewClickListener {
    @Inject
    MainPresenter mPresenter;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swieRefresh)
    SwipeRefreshLayout mSwipeContainer;
    private MoviesAdapter mAdater;
    private List<Movie> mMovies;
    private ProgressDialog mProgressDialog;

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.wassim.androidmvpbase.ui.views.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mAdater = new MoviesAdapter(this, this);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(
                        this, LinearLayoutManager.VERTICAL)
                );
        mRecyclerView.setAdapter(mAdater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.attachView(this);
        mPresenter.loadMovies();
        showProgress();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        hideProgress();
        mMovies = movies;
        mAdater.setMovies(mMovies);
        mAdater.notifyDataSetChanged();
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        hideProgress();
        mAdater.setMovies(Collections.<Movie>emptyList());
        mAdater.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_movies, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        hideProgress();
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_movies))
                .show();
    }

    @Override
    public void recyclerViewListClicked(View v, int position, int id) {
        Movie movie = mMovies.get(position);
        Intent intent = new Intent(this, SingleMovieActivity.class);
        intent.putExtra("movie", movie);

        ActivityOptionsCompat activityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,

                // Now we provide a list of Pair items which contain the
                // view we can transitioning
                // from, and the name of the view it is transitioning to,
                // in the launched activity
                new Pair<View, String>(v.findViewById(R.id.movie_image),
                        SingleMovieActivity.VIEW_NAME_HEADER_IMAGE));

        // Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        // END_INCLUDE(start_activity)

    }

    private void showProgress() {
        mProgressDialog.show();
    }

    private void hideProgress() {
        mProgressDialog.dismiss();
    }
}
