package com.pokhare.mymoviedb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "DebugActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment tvShowFragment = fm.findFragmentById(R.id.fragmentContainer_popularTvShows);
        Fragment movieFragment = fm.findFragmentById(R.id.fragmentContainer_popularMovies);

        if (tvShowFragment == null) {
            tvShowFragment = new TvShowFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer_popularTvShows, tvShowFragment)
                    .commit();
        }

        if (movieFragment == null) {
            movieFragment = new MovieFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer_popularMovies, movieFragment)
                    .commit();
        }
    }
}
