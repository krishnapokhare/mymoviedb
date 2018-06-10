package com.pokhare.mymoviedb.activities;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.fragments.CastDetailsFragment;
import com.pokhare.mymoviedb.fragments.MainFragment;
import com.pokhare.mymoviedb.fragments.MovieDetailFragment;
import com.pokhare.mymoviedb.helpers.VolleyRequester;
import com.pokhare.mymoviedb.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//import com.pokhare.mymoviedb.adapters.SearchAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "DebugActivity";
    //    private SearchAdapter mSearchAdapter;
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

//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
////        searchView.setIconifiedByDefault(false);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.search:
//                onSearchRequested();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final Menu tempMenu = menu;
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        final SearchSuggestionsAdapter searchSuggestionsAdapter = new SearchSuggestionsAdapter(this);
        searchView.setSuggestionsAdapter(searchSuggestionsAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
//                searchView.clearFocus();
//                searchView.setIconified(false);
                tempMenu.findItem(R.id.action_search).collapseActionView();
                Cursor searchCursor = searchSuggestionsAdapter.getCursor();
                if (searchCursor.moveToPosition(position)) {
                    int id = searchCursor.getInt(0);
                    String movieType = searchCursor.getString(2);

                    switch (movieType) {
                        case "movie": {
                            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(id);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.mainContainer, movieDetailFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        }
                        case "tv":

                            break;
                        case "person": {
                            CastDetailsFragment castDetailsFragment = CastDetailsFragment.newInstance(id);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.mainContainer, castDetailsFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    static class SearchSuggestionsAdapter extends SimpleCursorAdapter {
        private static final String[] mFields = {"_id", "name", "type"};
        private static final String[] mVisible = {"name", "type"};
        private static final int[] mViewIds = {R.id.list_item_movietitle, R.id.list_item_movietype};
        private Context ctx;


        public SearchSuggestionsAdapter(Context context) {
            super(context, R.layout.search_list_item_view, null, mVisible, mViewIds, 0);
            ctx = context;
        }

        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            return new MainActivity.SearchSuggestionsAdapter.SuggestionsCursor(constraint, ctx);
        }


        private static class SuggestionsCursor extends AbstractCursor {
            private ArrayList<SearchResult> mResults;

            public SuggestionsCursor(CharSequence constraint, Context context) {
                mResults = new ArrayList<SearchResult>();
                if (constraint != null) {
                    Log.i("SuggestionsCursor", constraint.toString());
//                    Map<Integer, String> map = new HashMap<Integer, String>();

                    RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
                    String url = "https://api.themoviedb.org/3/search/multi?api_key=150bd9f4531ed5941c9ce34a39109b57&language=en-US&query=" + constraint.toString() + "&page=1&include_adult=false";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), requestFuture, requestFuture);
                    VolleyRequester.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                    try {
                        JSONObject object = requestFuture.get(10, TimeUnit.SECONDS);
                        JSONArray searchResults = object.getJSONArray("results");
                        for (int i = 0; i < searchResults.length(); i++) {
                            try {
                                JSONObject jsonObject = searchResults.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String mediaType = jsonObject.getString("media_type");
                                Log.i("mResults", id + ":" + mediaType);
                                switch (mediaType) {
                                    case "movie":
                                        Log.i("mResultsMovie", "inside : movie");
                                        Log.i("mResultsMovie", jsonObject.getString("title"));
                                        mResults.add(new SearchResult(id, jsonObject.getString("title"), mediaType));
                                        break;
                                    case "tv":
                                        Log.i("mResultsMovie", "inside : tv");
                                        Log.i("mResultsTV", jsonObject.getString("name"));
                                        mResults.add(new SearchResult(id, jsonObject.getString("name"), mediaType));
                                        break;
                                    case "person":
                                        Log.i("mResultsPerson", "inside : person");
                                        Log.i("mResultsTV", jsonObject.getString("name"));
                                        mResults.add(new SearchResult(id, jsonObject.getString("name"), mediaType));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    Log.i("mResultsSize", String.valueOf(mResults.size()));
                }
            }

            public ArrayList<SearchResult> getmResults() {
                return mResults;
            }

            @Override
            public int getCount() {
                return mResults.size();
            }

            @Override
            public String[] getColumnNames() {
                Log.i("getLong", "getColumnNames");
                return mFields;
            }

            @Override
            public long getLong(int column) {
                Log.i("getLong", String.valueOf(column));
                if (column == 0) {

                    return mResults.get(mPos).getId();
                }
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public String getString(int column) {
                Log.i("getString", String.valueOf(column));
                if (column == 1) {
                    return mResults.get(mPos).getName();
                } else if (column == 2) {
                    return mResults.get(mPos).getType();
                }
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public short getShort(int column) {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public int getInt(int column) {
                if (column == 0) {
                    return mResults.get(mPos).getId();
                }
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public float getFloat(int column) {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public double getDouble(int column) {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public boolean isNull(int column) {
                return false;
            }
        }
    }
}
