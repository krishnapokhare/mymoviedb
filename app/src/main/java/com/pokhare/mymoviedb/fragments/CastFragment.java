package com.pokhare.mymoviedb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.adapters.CastRecyclerViewAdapter;
import com.pokhare.mymoviedb.models.FeaturedCast;

import java.util.ArrayList;
import java.util.List;

///**
// * A fragment representing a list of Items.
// * <p/>
// * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
// * interface.
// */
public class CastFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private ArrayList<FeaturedCast> featuredCastList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CastFragment() {
    }

    @SuppressWarnings("unused")
    public static CastFragment newInstance(List<FeaturedCast> featuredCastList) {
        CastFragment fragment = new CastFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("featuredCastList", (ArrayList<? extends Parcelable>) featuredCastList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //featuredCastList = getArguments().getInt(ARG_COLUMN_COUNT);
            featuredCastList = getArguments().getParcelableArrayList("featuredCastList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            int mColumnCount = 3;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new CastRecyclerViewAdapter(featuredCastList, new CastRecyclerViewAdapter.OnListFragmentInteractionListener() {
                @Override
                public void castFragmentOnClick(FeaturedCast featuredCast) {
                    CastDetailsFragment castDetailsFragment = CastDetailsFragment.newInstance(featuredCast.getId());
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, castDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }));
        }
        return view;
    }
}
