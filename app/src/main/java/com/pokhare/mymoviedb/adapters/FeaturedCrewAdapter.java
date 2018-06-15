package com.pokhare.mymoviedb.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.models.FeaturedCrew;

import java.util.List;

public class FeaturedCrewAdapter extends RecyclerView.Adapter<FeaturedCrewAdapter.ViewHolder> {
    private List<FeaturedCrew> featuredCrewList;

    public FeaturedCrewAdapter(List<FeaturedCrew> featuredCrewList) {
        this.featuredCrewList = featuredCrewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.featured_crew_layout, parent, false);
        return new FeaturedCrewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeaturedCrew featuredCrew=featuredCrewList.get(position);
        holder.featuredCrewNameTextView.setText(featuredCrew.getName());
        holder.featuredCrewRoleTextView.setText(featuredCrew.getRole());
    }

    public void setValues(List<FeaturedCrew> featuredCrewList) {
        this.featuredCrewList = featuredCrewList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return featuredCrewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView featuredCrewNameTextView;
        TextView featuredCrewRoleTextView;
        View mView;

        public ViewHolder(View view) {
            super(view);
            mView=view;
            featuredCrewNameTextView=view.findViewById(R.id.featuredCrewNameTextView);
            featuredCrewRoleTextView=view.findViewById(R.id.featuredCrewRoleTextView);
        }
    }
}
