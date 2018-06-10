package com.pokhare.mymoviedb.adapters;

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
import com.pokhare.mymoviedb.models.FeaturedCast;

import java.util.List;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder> {

    private final List<FeaturedCast> castList;
    private final OnListFragmentInteractionListener mListener;

    public CastRecyclerViewAdapter(List<FeaturedCast> items, OnListFragmentInteractionListener listener) {
        castList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.featuredCast = castList.get(position);
        holder.castNameTextView.setText(holder.featuredCast.getName());
        holder.castCharacterTextView.setText(holder.featuredCast.getCharacter());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(holder.castImageView.getContext());
        GlideApp.with(holder.castImageView.getContext())
                .load(ApiHelper.getInstance().GetImageUrl(holder.featuredCast.getImageUrl(), "w92"))
                .placeholder(circularProgressDrawable)
                //.fallback(R.drawable.default_person)
                .error(R.drawable.image_person)
                .into(holder.castImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.castFragmentOnClick(holder.featuredCast);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public interface OnListFragmentInteractionListener {
        void castFragmentOnClick(FeaturedCast featuredCast);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mIdView;
        public final TextView castNameTextView;
        private final ImageView castImageView;
        private final TextView castCharacterTextView;
        public FeaturedCast featuredCast;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
            castNameTextView = view.findViewById(R.id.castNameTextView);
            castImageView = view.findViewById(R.id.castImageView);
            castCharacterTextView = view.findViewById(R.id.castCharacterTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + castNameTextView.getText() + "'";
        }
    }
}
