package com.pokhare.mymoviedb.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.activities.MainActivity;
import com.pokhare.mymoviedb.adapters.CastRecyclerViewAdapter;
import com.pokhare.mymoviedb.adapters.FeaturedCastAdapter;
import com.pokhare.mymoviedb.adapters.FeaturedCrewAdapter;
import com.pokhare.mymoviedb.helpers.ApiCallbackListener;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;
import com.pokhare.mymoviedb.models.FeaturedCrew;
import com.pokhare.mymoviedb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MOVIE_ID = "movieId";

    // TODO: Rename and change types of parameters
    private Integer movieId;
    private String mParam2;
    private Movie movie;
    ProgressBar ratingProgressBar;
    TextView txtProgressbar;
    ImageView movieImageView;
    private TextView overviewTextView;
    private List<FeaturedCrew> featuredCrewList;
    private RecyclerView featuredCrewRecyclerView;
    private List<FeaturedCast> featuredCastList;
    private RecyclerView featuredCastRecyclerView;
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView viewAllCastTextView;

    public MovieDetailFragment() {
        // Required empty public constructor
    }
    public static MovieDetailFragment newInstance(Integer movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, movieId);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MOVIE_ID, movieId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieId = savedInstanceState.getInt(MOVIE_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ratingProgressBar = view.findViewById(R.id.ratingProgressBar);
        txtProgressbar = view.findViewById(R.id.txtProgress);
        movieImageView = view.findViewById(R.id.detailsImageView);
        overviewTextView = view.findViewById(R.id.overviewTextView);
        movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
        moviePosterImageView = view.findViewById(R.id.detailsImagePoster);
        viewAllCastTextView = view.findViewById(R.id.viewAllCastTextView);
        featuredCrewRecyclerView = view.findViewById(R.id.featuredCrewRecyclerView);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        featuredCrewRecyclerView.setLayoutManager(gridLayoutManager1); // set LayoutManager to RecyclerView
        featuredCastRecyclerView = view.findViewById(R.id.featuredCastRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        featuredCastRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        featuredCrewList = new ArrayList<FeaturedCrew>();
        featuredCastList = new ArrayList<FeaturedCast>();

        ApiHelper.getInstance().GetJsonObject("movie/" + movieId + "?language=en-US&page=1&",
                new GetMovieDetailsTask(), getContext().getApplicationContext());
        return view;
    }

    private void SetAllViewFields() {
        ratingProgressBar.setProgress((int) movie.getVote_average() * 10);
        txtProgressbar.setText((movie.getVote_average() * 10) + "%");
        overviewTextView.setText(movie.getOverview());
        movieTitleTextView.setText(movie.getTitle());

        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(getActivity());
        GlideApp.with(this)
                .load(ApiHelper.getInstance().GetImageUrl(movie.getBackdrop_path(), "w500"))
                .placeholder(circularProgressDrawable)
                .into(movieImageView);
        GlideApp.with(this)
                .load(ApiHelper.getInstance().GetImageUrl(movie.getPoster_path(), "w154"))
                .placeholder(circularProgressDrawable)
                .into(moviePosterImageView);
    }

    class GetMovieDetailsTask implements ApiCallbackListener {

        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("ApiHelper", "Success");
            try {

                movie = Movie.Factory.NewMovieWithAdditionalFields(result);
                Log.i("ApiHelper", movie.getTitle());
                ((MainActivity) getActivity()).setActionBarTitle(movie.getTitle());
                SetAllViewFields();
                ApiHelper.getInstance().GetJsonObject("movie/" + movie.getId() + "/credits?language=en-US&",
                        new GetFeaturedCrewTask(), getContext().getApplicationContext());
                ApiHelper.getInstance().GetJsonObject("movie/" + movieId + "/credits?language=en-US&",
                        new GetFeaturedCastTask(), getContext().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class GetFeaturedCrewTask implements ApiCallbackListener {
        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("MovieDbHelperTest", "Success");
            // If the response is JSONObject instead of expected JSONArray
            try {
                featuredCrewList.clear();
                JSONArray resultsArray = result.getJSONArray("crew");
                for (int i = 0; i < resultsArray.length() && i < 4; i++) {
                    JSONObject jsonObject = resultsArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String role = jsonObject.getString("job");
                    FeaturedCrew featuredCrew = new FeaturedCrew(name, role);
                    featuredCrewList.add(featuredCrew);
                }

                FeaturedCrewAdapter featuredCrewAdapter = new FeaturedCrewAdapter(featuredCrewList);
                featuredCrewRecyclerView.setAdapter(featuredCrewAdapter); // set the Adapter to RecyclerView
            } catch (JSONException e) {
                e.printStackTrace();
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
                featuredCastList.clear();
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
                FeaturedCastAdapter featuredCastAdapter = new FeaturedCastAdapter(featuredCastList.subList(0, 9), new CastRecyclerViewAdapter.OnListFragmentInteractionListener() {
                    @Override
                    public void castFragmentOnClick(FeaturedCast featuredCast) {
                        CastDetailsFragment castDetailsFragment = CastDetailsFragment.newInstance(featuredCast.getId());
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainContainer, castDetailsFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                featuredCastRecyclerView.setAdapter(featuredCastAdapter);
                viewAllCastTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CastFragment castFragment = CastFragment.newInstance(featuredCastList);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainContainer, castFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
