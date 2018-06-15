package com.pokhare.mymoviedb.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pokhare.mymoviedb.helpers.ApiCallbackListener;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
    private final Application mApplication;
    private MutableLiveData<List<Movie>> movies;


    public MoviesViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
    }


    @NonNull
    public LiveData<List<Movie>> getMovies() {
        if (movies == null) {
            movies = new MutableLiveData<List<Movie>>();
            loadMovies();
        }
        return movies;
    }

    private void loadMovies() {
        ApiHelper.getInstance()
                .GetJsonObject("movie/popular?language=en-US&page=1&", new GetPopularMoviesTask(), mApplication.getApplicationContext());
    }

    class GetPopularMoviesTask implements ApiCallbackListener {

        @Override
        public void onTaskCompleted(JSONObject result) {
            try {
                JSONArray resultsArray = result.getJSONArray("results");
                List<Movie> movieList = new ArrayList<Movie>();
                for (int i = 0; i < resultsArray.length(); i++) {
                    Log.i("ApiHelper", resultsArray.getJSONObject(i).getString("title"));
                    Movie movie = Movie.Factory.NewMovieWithBasicFields(resultsArray.getJSONObject(i));
                    Log.i("MovieDbHelperTest", movie.getTitle());
                    movieList.add(movie);
                }
                movies.setValue(movieList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
