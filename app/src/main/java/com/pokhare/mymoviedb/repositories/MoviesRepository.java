package com.pokhare.mymoviedb.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.pokhare.mymoviedb.models.Movie;

import java.util.List;

public class MoviesRepository {

    public MoviesRepository(Application application) {

    }

    public LiveData<List<Movie>> getPopularMovies() {
        return null;
    }
}
