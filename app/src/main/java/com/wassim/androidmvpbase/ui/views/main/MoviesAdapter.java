package com.wassim.androidmvpbase.ui.views.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wassim.androidmvpbase.R;
import com.wassim.androidmvpbase.data.local.database.Movie;
import com.wassim.androidmvpbase.util.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;
    private RecyclerViewClickListener mListener;


    MoviesAdapter(Context mContext, RecyclerViewClickListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mMovies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.mMovies.clear();
        this.mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.with(mContext).load(movie.image()).into(holder.movieImage);
        holder.movieTitle.setText(movie.title());
        holder.movieGenre.setText(movie.genre());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_image)
        ImageView movieImage;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_genre)
        TextView movieGenre;
        @BindView(R.id.item_container)
        LinearLayout itemContainer;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.recyclerViewListClicked(v, this.getLayoutPosition(), v.getId());
        }
    }

}
