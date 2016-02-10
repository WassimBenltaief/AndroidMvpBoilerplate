package com.wassim.androidmvpbase.ui.views.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wassim.androidmvpbase.R;
import com.wassim.androidmvpbase.data.model.Movie;
import com.wassim.androidmvpbase.ui.base.BaseActivity;
import com.wassim.androidmvpbase.ui.views.single.SingleMovieActivity;
import com.wassim.androidmvpbase.util.DialogFactory;
import com.wassim.androidmvpbase.util.DividerItemDecoration;
import com.wassim.androidmvpbase.util.RecyclerViewClickListener;

import java.util.ArrayList;
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
    @Bind(R.id.network_status)
    TextView mNetworkStatusTextView;
    private MoviesAdapter mAdapter;
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
        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(
                        this, LinearLayoutManager.VERTICAL)
                );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.attachView(this);
        mPresenter.getNetworkStatus();

        // testing Gcm Network manager
        // for testing purpose we count on SyncService to get
        // list of movies and post a bus event to load the list

        List<Movie> movies = makeMovies();
        // mPresenter.loadCashedMovies();
        showMovies(movies);
        Toast.makeText(this, "waiting or Gcm Network Manager One Off Task", Toast.LENGTH_LONG)
                .show();

        // end testing Gcm Network manager
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadCashedMovies();
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
        mAdapter.setMovies(mMovies);
        mAdapter.notifyDataSetChanged();
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        hideProgress();
        mAdapter.setMovies(Collections.<Movie>emptyList());
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }


    // set Network status notification depending on Network Status
    /** TODO
     * add animation fade in fade out to the text view and get out the TextView from
     * the CoordinatesLayout
     */


    @Override
    public void showNetworkStatus(boolean status) {
        if(status){
            mNetworkStatusTextView.setText(R.string.connected);
            mNetworkStatusTextView.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.light_green));
        } else {
            mNetworkStatusTextView.setText(R.string.network_disconnected);
            mNetworkStatusTextView.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    // for testing the Gcm Network manager, we return first a fake cached data
    // assign it to the Recycler and wait for the One Off Task to fire
    private List<Movie> makeMovies() {
        String jsonMovie = "{\n" +
                "\"id\": 1,\n" +
                "\"title\": \"Dawn of the Planet of the Apes\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-zzPZ3rv8IQs/Vq4Krg8TEDI/" +
                "AAAAAAAAAp8/efWYKQxO_vQ/s800-Ic42/1.jpg\",\n" +
                "\"rating\": 8.3,\n" +
                "\"releaseYear\": 2014,\n" +
                "\"genre\": \"Action\",\n" +
                "\"synopsis\": \"A growing nation of genetically evolved apes led by Caesar " +
                "is threatened by a band of human survivors of the devastating " +
                "virus unleashed a decade earlier.\"\n" +
                "}";

        String jsonMovie2 = "{\n" +
                "\"id\": 2,\n" +
                "\"title\": \"District 9\",\n" +
                "\"image\": \"https://lh3.googleusercontent.com/-K8J4efkHoQM/Vq4KsRTG1yI/" +
                "AAAAAAAAAqU/UesoWr5cSu8/s800-Ic42/2.jpg\",\n" +
                "\"rating\": 8,\n" +
                "\"releaseYear\": 2009,\n" +
                "\"genre\": \"Sci-Fi\",\n" +
                "\"synopsis\": \"An extraterrestrial race forced to live in slum-like " +
                "conditions on Earth suddenly finds a kindred spirit in a government agent " +
                "who is exposed to their biotechnology.\"\n" +
                "}";


        Movie movie = new Gson().fromJson(jsonMovie, Movie.class);
        Movie movie2 = new Gson().fromJson(jsonMovie2, Movie.class);
        List<Movie> mMovies = new ArrayList<>();
        mMovies.add(movie);
        mMovies.add(movie2);
        return mMovies;
    }


}
