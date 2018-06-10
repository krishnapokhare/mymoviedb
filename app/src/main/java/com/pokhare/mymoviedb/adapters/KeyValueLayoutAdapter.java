package com.pokhare.mymoviedb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;

import java.util.List;

public class KeyValueLayoutAdapter extends RecyclerView.Adapter<KeyValueLayoutAdapter.ViewHolder> {

    private final List<List<String>> keyValueList;

    public KeyValueLayoutAdapter(List<List<String>> keyValueList) {
        this.keyValueList = keyValueList;
    }

    @Override
    public KeyValueLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.key_value_layout, parent, false);
        return new KeyValueLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KeyValueLayoutAdapter.ViewHolder holder, final int position) {
        List<String> personalDetail = keyValueList.get(position);
        holder.personalDetailsLabelTextView.setText(personalDetail.get(0));
        holder.personalDetailsValueTextView.setText(personalDetail.get(1));
    }

    @Override
    public int getItemCount() {
        return keyValueList.size();
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
