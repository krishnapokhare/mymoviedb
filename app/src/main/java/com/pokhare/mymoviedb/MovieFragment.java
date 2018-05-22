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

import com.pokhare.mymoviedb.adapters.MoviesAdapter;
import com.pokhare.mymoviedb.models.Movie;
import com.pokhare.mymoviedb.models.TvShow;

import java.util.List;

import static com.pokhare.mymoviedb.MainActivity.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private List<Movie> movies;
    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        movies= Movie.Factory.GetPopularMovies();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView popularMoviesRecyclerView = view.findViewById(R.id.recyclerView_popularMovies);
        if (popularMoviesRecyclerView == null) {
            Log.i(LOG_TAG, "RecyclerView is null");
        }
        popularMoviesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (movies.size() > 0 & popularMoviesRecyclerView != null) {
            popularMoviesRecyclerView.setAdapter(new MoviesAdapter(movies));
        }
        popularMoviesRecyclerView.setLayoutManager(MyLayoutManager);
        MoviesAdapter moviesAdapter = new MoviesAdapter(movies);
        popularMoviesRecyclerView.setAdapter(moviesAdapter);
        return view;
    }

}
