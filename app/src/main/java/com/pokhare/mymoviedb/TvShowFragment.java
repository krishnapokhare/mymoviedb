package com.pokhare.mymoviedb;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokhare.mymoviedb.adapters.TvShowsAdapter;
import com.pokhare.mymoviedb.models.TvShow;

import java.util.List;

import static com.pokhare.mymoviedb.MainActivity.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {


    private List<TvShow> tvShows;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        tvShows=TvShow.Factory.GetPopularTvShows();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView popularTvShowsRecyclerView = view.findViewById(R.id.recyclerView_popularTvShows);
        if (popularTvShowsRecyclerView == null) {
            Log.i(LOG_TAG, "RecyclerView is null");
        }
        popularTvShowsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (tvShows.size() > 0 & popularTvShowsRecyclerView != null) {
            popularTvShowsRecyclerView.setAdapter(new TvShowsAdapter(tvShows));
        }
        popularTvShowsRecyclerView.setLayoutManager(MyLayoutManager);
        TvShowsAdapter tvShowsAdapter = new TvShowsAdapter(tvShows);
        popularTvShowsRecyclerView.setAdapter(tvShowsAdapter);
        return view;
    }

}
