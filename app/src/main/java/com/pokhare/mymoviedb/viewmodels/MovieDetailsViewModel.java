package com.pokhare.mymoviedb.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pokhare.mymoviedb.helpers.ApiCallbackListener;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.models.FeaturedCast;
import com.pokhare.mymoviedb.models.FeaturedCrew;
import com.pokhare.mymoviedb.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailsViewModel extends AndroidViewModel {
    private final Application mApplication;
    private MutableLiveData<Movie> movieMutableLiveData;
    private int movieId;
    private MutableLiveData<List<List<String>>> movieFactsMutableList;
    private MutableLiveData<List<FeaturedCrew>> featuredCrewMutableList;
    private MutableLiveData<List<FeaturedCast>> featuredCastMutableList;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
    }

    public void setMovieId(int id) {
        movieId = id;
        Log.d("MovieDetailsViewModel", String.valueOf(id));
        movieMutableLiveData = null;
        movieFactsMutableList = null;
        featuredCrewMutableList = null;
        featuredCastMutableList = null;
    }

    @NonNull
    public LiveData<Movie> getMovie() {
        if (movieMutableLiveData == null) {
            movieMutableLiveData = new MutableLiveData<Movie>();
            loadMovieDetails();
        }
        return movieMutableLiveData;
    }

    public LiveData<List<List<String>>> getMovieFacts() {
        if (movieFactsMutableList == null) {
            movieFactsMutableList = new MutableLiveData<List<List<String>>>();
        }
        return movieFactsMutableList;
    }

    public LiveData<List<FeaturedCrew>> getFeaturedCrew() {
        if (featuredCrewMutableList == null) {
            featuredCrewMutableList = new MutableLiveData<List<FeaturedCrew>>();
        }
        return featuredCrewMutableList;
    }

    public LiveData<List<FeaturedCast>> getFeaturedCast() {
        if (featuredCastMutableList == null) {
            featuredCastMutableList = new MutableLiveData<List<FeaturedCast>>();
        }
        return featuredCastMutableList;
    }

    private void loadMovieDetails() {
        ApiHelper.getInstance().GetJsonObject("movie/" + movieId + "?language=en-US&page=1&",
                new GetMovieDetailsTask(), mApplication.getApplicationContext());
    }

    class GetMovieDetailsTask implements ApiCallbackListener {

        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("ApiHelper", "Success");
            try {

                Movie movie = Movie.Factory.NewMovieWithAdditionalFields(result);
                Log.i("ApiHelper", movie.getTitle());
                GetAllMovieFacts(movie);
                ApiHelper.getInstance().GetJsonObject("movie/" + movie.getId() + "/credits?language=en-US&",
                        new GetFeaturedCrewTask(), mApplication.getApplicationContext());
                ApiHelper.getInstance().GetJsonObject("movie/" + movie.getId() + "/credits?language=en-US&",
                        new GetFeaturedCastTask(), mApplication.getApplicationContext());
                movieMutableLiveData.setValue(movie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void GetAllMovieFacts(Movie movie) {
            List<List<String>> movieFactsList = new ArrayList<List<String>>();
            movieFactsList.add(Arrays.asList("Status", movie.getStatus()));
            movieFactsList.add(Arrays.asList("Original Language", movie.getOriginal_language()));
            movieFactsList.add(Arrays.asList("Runtime", formatRuntime(movie.getRuntime())));
            movieFactsList.add(Arrays.asList("Budget", "$" + movie.getBudget()));
            movieFactsMutableList.setValue(movieFactsList);
        }

        private String formatRuntime(int runtime) {
            if (runtime <= 60) {
                return runtime + "m";
            } else {
                int hours = runtime / 60;
                int mins = runtime % 60;
                if (mins > 0) {
                    return hours + "h " + mins + "m";
                } else {
                    return hours + "hrs";
                }
            }
        }
    }

    class GetFeaturedCrewTask implements ApiCallbackListener {
        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("MovieDbHelperTest", "Success");
            // If the response is JSONObject instead of expected JSONArray
            try {
                List<FeaturedCrew> featuredCrewList = new ArrayList<FeaturedCrew>();
                JSONArray resultsArray = result.getJSONArray("crew");
                for (int i = 0; i < resultsArray.length() && i < 4; i++) {
                    JSONObject jsonObject = resultsArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String role = jsonObject.getString("job");
                    FeaturedCrew featuredCrew = new FeaturedCrew(name, role);
                    featuredCrewList.add(featuredCrew);
                }
                featuredCrewMutableList.setValue(featuredCrewList);
//                FeaturedCrewAdapter featuredCrewAdapter = new FeaturedCrewAdapter(featuredCrewList);
//                featuredCrewRecyclerView.setAdapter(featuredCrewAdapter); // set the Adapter to RecyclerView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class GetFeaturedCastTask implements ApiCallbackListener {

        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("MovieDbHelperTest", "Success");
            // If the response is JSONObject instead of expected JSONArray
            try {
                List<FeaturedCast> featuredCastList = new ArrayList<FeaturedCast>();
                JSONArray resultsArray = result.getJSONArray("cast");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject jsonObject = resultsArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String character = jsonObject.getString("character");
                    String profile_path = jsonObject.getString("profile_path");
                    FeaturedCast featuredCast = new FeaturedCast(id, name, character, profile_path);
                    featuredCastList.add(featuredCast);
                }
                featuredCastMutableList.setValue(featuredCastList);
//                FeaturedCastAdapter featuredCastAdapter = new FeaturedCastAdapter(featuredCastList.subList(0, 9), new CastRecyclerViewAdapter.OnListFragmentInteractionListener() {
//                    @Override
//                    public void castFragmentOnClick(FeaturedCast featuredCast) {
//                        CastDetailsFragment castDetailsFragment = CastDetailsFragment.newInstance(featuredCast.getId());
//                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.mainContainer, castDetailsFragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//                });
//                featuredCastRecyclerView.setAdapter(featuredCastAdapter);
//                viewAllCastTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CastFragment castFragment = CastFragment.newInstance(featuredCastList);
//                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.mainContainer, castFragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
