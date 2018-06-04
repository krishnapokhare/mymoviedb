package com.pokhare.mymoviedb.adapters;

import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.helpers.DbHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;

import java.util.List;

public class FeaturedCastAdapter extends RecyclerView.Adapter<FeaturedCastAdapter.ViewHolder> {
    private List<FeaturedCast> featuredCastList;
    CastRecyclerViewAdapter.OnListFragmentInteractionListener mListener;

    public FeaturedCastAdapter(List<FeaturedCast> featuredCastList, CastRecyclerViewAdapter.OnListFragmentInteractionListener mListener) {
        this.featuredCastList = featuredCastList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.featured_cast_layout, parent, false);
        return new FeaturedCastAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final FeaturedCast featuredCast = featuredCastList.get(position);
        holder.featuredCastNameTextView.setText(featuredCast.getName());
        holder.featuredCastCharacterTextView.setText(featuredCast.getCharacter());
        Log.d("FeaturedCastAdapter",featuredCast.getImageUrl());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(holder.featuredCastImageView.getContext());
        GlideApp.with(holder.featuredCastImageView.getContext())
                .load(DbHelper.IMAGE_BASE_URL + "/w92" + featuredCast.getImageUrl())
                .placeholder(circularProgressDrawable)
                .into(holder.featuredCastImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.castFragmentOnClick(featuredCast);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return featuredCastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView featuredCastImageView;
        public TextView featuredCastNameTextView;
        public TextView featuredCastCharacterTextView;
        public View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            featuredCastImageView = view.findViewById(R.id.featuredCastImageView);
            featuredCastNameTextView = view.findViewById(R.id.featuredCastNameTextView);
            featuredCastCharacterTextView = view.findViewById(R.id.featuredCastCharacterTextView);
        }
    }
}
