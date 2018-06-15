package com.pokhare.mymoviedb.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.activities.MainActivity;
import com.pokhare.mymoviedb.adapters.MoviesAdapter;
import com.pokhare.mymoviedb.adapters.TvShowsAdapter;
import com.pokhare.mymoviedb.models.Movie;
import com.pokhare.mymoviedb.models.TvShow;
import com.pokhare.mymoviedb.viewmodels.MovieDetailsViewModel;
import com.pokhare.mymoviedb.viewmodels.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pokhare.mymoviedb.activities.MainActivity.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private List<Movie> movies;
    private List<TvShow> tvShows;
    private MoviesAdapter moviesAdapter;
    private TvShowsAdapter tvShowsAdapter;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MoviesViewModel viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesList) {
                movies = moviesList;
                moviesAdapter.setValues(moviesList);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context AppContext = getContext().getApplicationContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        movies = new ArrayList<Movie>();
        tvShows = new ArrayList<TvShow>();
        ((MainActivity) getActivity()).setActionBarTitle("My Movie Database");
        //Movie
        RecyclerView popularMoviesRecyclerView = view.findViewById(R.id.recyclerView_popularMovies);
        if (popularMoviesRecyclerView == null) {
            Log.i(LOG_TAG, "RecyclerView is null");
        }
        popularMoviesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MovieLayoutManager = new LinearLayoutManager(getActivity());
        MovieLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        if (movies.size() > 0 & popularMoviesRecyclerView != null) {
//            popularMoviesRecyclerView.setAdapter(new MoviesAdapter(movies));
//        }
        popularMoviesRecyclerView.setLayoutManager(MovieLayoutManager);
        moviesAdapter = new MoviesAdapter(movies, new MoviesAdapter.MoviesAdapterListener() {
            @Override
            public void onMovieItemClick(View v, int position) {
                MovieDetailsViewModel viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
                viewModel.setMovieId(movies.get(position).getId());
                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movies.get(position).getId());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, movieDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        popularMoviesRecyclerView.setAdapter(moviesAdapter);
//        ApiHelper.getInstance()
//                .GetJsonObject("movie/popular?language=en-US&page=1&", new GetPopularMoviesTask(), AppContext);

//        //TV Shows
//        RecyclerView popularTvShowsRecyclerView = view.findViewById(R.id.recyclerView_popularTvShows);
//        if (popularTvShowsRecyclerView == null) {
//            Log.i(LOG_TAG, "RecyclerView is null");
//        }
//        popularTvShowsRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager TvShowLayoutManager = new LinearLayoutManager(getActivity());
//        TvShowLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        if (tvShows.size() > 0 & popularTvShowsRecyclerView != null) {
//            popularTvShowsRecyclerView.setAdapter(new TvShowsAdapter(tvShows));
//        }
//        popularTvShowsRecyclerView.setLayoutManager(TvShowLayoutManager);
//
//        tvShowsAdapter = new TvShowsAdapter(tvShows);
//        popularTvShowsRecyclerView.setAdapter(tvShowsAdapter);
//        ApiHelper.getInstance()
//                .GetJsonObject("tv/popular?language=en-US&page=1&", new GetPopularTvShowsTask(), AppContext);

        return view;
    }

//    class GetPopularMoviesTask implements ApiCallbackListener {
//
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            try {
//                JSONArray resultsArray = result.getJSONArray("results");
//                movies.clear();
//                for (int i = 0; i < resultsArray.length(); i++) {
//                    Log.i("ApiHelper", resultsArray.getJSONObject(i).getString("title"));
//                    Movie movie = Movie.Factory.NewMovieWithBasicFields(resultsArray.getJSONObject(i));
//                    Log.i("MovieDbHelperTest", movie.getTitle());
//
//                    movies.add(movie);
//                    Log.i("MovieDbHelperTest", String.valueOf(movies.size()));
//                }
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        moviesAdapter.notifyDataSetChanged();
//                    }
//                });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class GetPopularTvShowsTask implements ApiCallbackListener {
//
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            try {
//                JSONArray resultsArray = result.getJSONArray("results");
//                tvShows.clear();
//                for (int i = 0; i < 4; i++) {
//                    Log.i("ApiHelper", resultsArray.getJSONObject(i).getString("name"));
//                    TvShow tvShow = TvShow.Factory.NewTvShow(resultsArray.getJSONObject(i));
//                    Log.i("DbHelperTest", tvShow.getName());
//
//                    tvShows.add(tvShow);
//                    Log.i("DbHelperTest", String.valueOf(tvShows.size()));
//                }
////
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvShowsAdapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        }
//    }
}
