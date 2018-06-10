package com.pokhare.mymoviedb.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pokhare.mymoviedb.models.Movie;
import com.pokhare.mymoviedb.repositories.MoviesRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
    private MoviesRepository moviesRepository;
    private LiveData<List<Movie>> mPopularMovies;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        mPopularMovies = moviesRepository.getPopularMovies();
    }

    LiveData<List<Movie>> getmPopularMovies() {
        return mPopularMovies;
    }
}
