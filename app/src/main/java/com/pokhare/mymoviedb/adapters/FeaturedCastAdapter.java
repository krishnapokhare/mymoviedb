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

import com.bumptech.glide.Glide;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;
import com.pokhare.mymoviedb.models.FeaturedCrew;

import java.util.List;

public class FeaturedCastAdapter extends RecyclerView.Adapter<FeaturedCastAdapter.ViewHolder> {
    private List<FeaturedCast> featuredCastList;

    public FeaturedCastAdapter(List<FeaturedCast> featuredCastList) {
        this.featuredCastList = featuredCastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.featured_cast_layout, parent, false);
        return new FeaturedCastAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeaturedCast featuredCast = featuredCastList.get(position);
        holder.featuredCastNameTextView.setText(featuredCast.getName());
        holder.featuredCastCharacterTextView.setText(featuredCast.getCharacter());
        Log.d("FeaturedCastAdapter",featuredCast.getImageUrl());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(holder.featuredCastImageView.getContext());
        GlideApp.with(holder.featuredCastImageView.getContext())
                .load(featuredCast.getImageUrl())
                .placeholder(circularProgressDrawable)
                .into(holder.featuredCastImageView);

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
