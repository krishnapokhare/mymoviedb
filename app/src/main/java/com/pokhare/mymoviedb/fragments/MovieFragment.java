package com.pokhare.mymoviedb.fragments;


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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.adapters.MoviesAdapter;
import com.pokhare.mymoviedb.helpers.DbHelper;
import com.pokhare.mymoviedb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.pokhare.mymoviedb.activities.MainActivity.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private List<Movie> movies;
    RecyclerView popularMoviesRecyclerView;
    MoviesAdapter moviesAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        movies = new ArrayList<Movie>();
        DbHelper helper = new DbHelper();
        helper.GetImageBaseUrl();
        //movies= Movie.Factory.GetPopularMovies();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        popularMoviesRecyclerView = view.findViewById(R.id.recyclerView_popularMovies);
        if (popularMoviesRecyclerView == null) {
            Log.i(LOG_TAG, "RecyclerView is null");
        }
        popularMoviesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        if (movies.size() > 0 & popularMoviesRecyclerView != null) {
//            popularMoviesRecyclerView.setAdapter(new MoviesAdapter(movies));
//        }
        popularMoviesRecyclerView.setLayoutManager(MyLayoutManager);
        moviesAdapter = new MoviesAdapter(movies, new MoviesAdapter.MoviesAdapterListener() {
            @Override
            public void onMovieItemClick(View v, int position) {
                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movies.get(position).getId());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, movieDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        popularMoviesRecyclerView.setAdapter(moviesAdapter);
        GetPopularMovies();

        return view;
    }

    public void GetPopularMovies() {
        Log.i("MovieDBHelper", "method:GetPopularMovies");
        DbHelper movieHelper = new DbHelper();
        movieHelper.get("movie/popular?language=en-US&page=1&", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("MovieDbHelperTest", "Success");
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray resultsArray = response.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); i++) {
//                    for (int i = 5; i >= 0; i--) {
                        Log.i("MovieDBHelper", resultsArray.getJSONObject(i).getString("title"));
                        Movie movie = Movie.Factory.NewMovieWithBasicFields(resultsArray.getJSONObject(i));
                        Log.i("MovieDbHelperTest", movie.getTitle());

                        movies.add(movie);
                        Log.i("MovieDbHelperTest", String.valueOf(i));
                    }
//
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            moviesAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("MovieDbHelperTest", String.valueOf(statusCode));
            }
        });
    }
}
