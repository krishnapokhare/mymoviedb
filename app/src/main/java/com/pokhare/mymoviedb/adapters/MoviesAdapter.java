package com.pokhare.mymoviedb.adapters;

import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    private MoviesAdapterListener moviesAdapterListener;

    public MoviesAdapter(List<Movie> movies, MoviesAdapterListener moviesAdapterListener) {
        this.movies = movies;
        this.moviesAdapterListener = moviesAdapterListener;
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
        if (movies != null) {
            Movie movie = movies.get(position);
            holder.mTitleTextView.setText(movie.getTitle());

            CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(holder.mCoverImageView.getContext());
            GlideApp.with(holder.mCoverImageView.getContext())
                    .load(ApiHelper.getInstance().GetImageUrl(movie.getBackdrop_path(), "w500"))
                    .placeholder(circularProgressDrawable)
                    .fallback(R.drawable.default_picture)
                    .error(R.drawable.default_picture)
                    .into(holder.mCoverImageView);
        } else {
            holder.mTitleTextView.setText("Movies not loaded properly. Please try again.");
        }
    }

    public interface MoviesAdapterListener {
        void onMovieItemClick(View v, int position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (movies != null)
            return movies.size();
        return 0;
    }

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
            mCoverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviesAdapterListener.onMovieItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}
