package com.pokhare.mymoviedb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;

import java.util.List;

public class CastPersonalDetailsAdapter extends RecyclerView.Adapter<CastPersonalDetailsAdapter.ViewHolder> {

    private final List<List<String>> castPersonalDetailsList;

    public CastPersonalDetailsAdapter(List<List<String>> castPersonalDetailsList) {
        this.castPersonalDetailsList = castPersonalDetailsList;
    }

    @Override
    public CastPersonalDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_personal_details, parent, false);
        return new CastPersonalDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CastPersonalDetailsAdapter.ViewHolder holder, final int position) {
        List<String> personalDetail = castPersonalDetailsList.get(position);
        holder.personalDetailsLabelTextView.setText(personalDetail.get(0));
        holder.personalDetailsValueTextView.setText(personalDetail.get(1));
    }

    @Override
    public int getItemCount() {
        return castPersonalDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mIdView;
        public final TextView personalDetailsLabelTextView;
        private final TextView personalDetailsValueTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
            personalDetailsLabelTextView = view.findViewById(R.id.personalDetailsLabel);
            personalDetailsValueTextView = view.findViewById(R.id.personalDetailsValue);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
