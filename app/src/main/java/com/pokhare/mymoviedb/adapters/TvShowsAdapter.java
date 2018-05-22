package com.pokhare.mymoviedb.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.models.TvShow;

import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.ViewHolder> {
    private List<TvShow> tvShows;
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
    public TvShowsAdapter(List<TvShow> tvShows) {
        this.tvShows=tvShows;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TvShowsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tvshow_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TvShow tvShow=tvShows.get(position);
        holder.mTitleTextView.setText(tvShow.getName());
//        holder.mCoverImageView
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
