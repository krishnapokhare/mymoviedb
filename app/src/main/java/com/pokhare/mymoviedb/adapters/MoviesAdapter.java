package com.pokhare.mymoviedb.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.helpers.DbHelper;
import com.pokhare.mymoviedb.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleTextView;
        public ImageView mCoverImageView;
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTitleTextView = v.findViewById(R.id.titleTextView);
            mCoverImageView = v.findViewById(R.id.coverImageView);
        }
    }
    public MoviesAdapter(List<Movie> movies) {
        this.movies=movies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tvshow_list_layout, parent, false);
        return new MoviesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        Movie movie=movies.get(position);
        holder.mTitleTextView.setText(movie.getTitle());
        Glide.with(holder.mCoverImageView.getContext()).load(DbHelper.IMAGE_BASE_URL + "/w500"+  movie.getPoster_path()).into(holder.mCoverImageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
    }
}
