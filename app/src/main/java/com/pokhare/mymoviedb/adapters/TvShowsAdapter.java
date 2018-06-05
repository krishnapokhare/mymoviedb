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
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(holder.mCoverImageView.getContext());
        GlideApp.with(holder.mCoverImageView.getContext())
                .load(ApiHelper.getInstance().GetImageUrl(tvShow.getBackdrop_path(), "w500"))
                .placeholder(circularProgressDrawable)
                .fallback(R.drawable.default_picture)
                .error(R.drawable.default_picture)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        String TAG="GlideLoadFailure";
//                        // Log the GlideException here (locally or with a remote logging framework):
//                        Log.e(TAG, "Load failed", e);
//
//                        // You can also log the individual causes:
//                        for (Throwable t : e.getRootCauses()) {
//                            Log.e(TAG, "Caused by", t);
//                        }
//                        // Or, to log all root causes locally, you can use the built in helper method:
//                        e.logRootCauses(TAG);
//
//                        return false; // Allow calling onLoadFailed on the Target.
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
                .into(holder.mCoverImageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
