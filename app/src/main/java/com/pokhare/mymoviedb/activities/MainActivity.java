package com.pokhare.mymoviedb.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.adapters.SearchAdapter;
import com.pokhare.mymoviedb.fragments.MainFragment;

//import com.pokhare.mymoviedb.adapters.SearchAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "DebugActivity";
    private SearchAdapter mSearchAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment mainFragment = fm.findFragmentById(R.id.mainContainer);
//        Fragment tvShowFragment = fm.findFragmentById(R.id.fragmentContainer_popularTvShows);
//        Fragment movieFragment = fm.findFragmentById(R.id.fragmentContainer_popularMovies);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.mainContainer, mainFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
