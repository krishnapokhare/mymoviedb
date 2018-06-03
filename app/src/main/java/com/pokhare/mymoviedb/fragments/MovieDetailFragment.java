package com.pokhare.mymoviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.adapters.FeaturedCastAdapter;
import com.pokhare.mymoviedb.adapters.FeaturedCrewAdapter;
import com.pokhare.mymoviedb.helpers.DbHelper;
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

import cz.msebera.android.httpclient.Header;

public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
//    private OnFragmentInteractionListener mListener;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movieId Parameter 1.
     *                //     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(Integer movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, movieId);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_PARAM1);
//            movie = getMovieDetails(movieId);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        featuredCrewList = new ArrayList<FeaturedCrew>();
        featuredCastList = new ArrayList<FeaturedCast>();
    }

    private void getMovieDetails(final Integer movieId) {
        Log.i("MovieDBHelper", "method:getMovieDetails");
        DbHelper movieHelper = new DbHelper();
        movieHelper.get("movie/" + movieId + "?language=en-US&page=1&", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("MovieDbHelperTest", "Success");
                // If the response is JSONObject instead of expected JSONArray
                try {
//                    JSONArray resultsArray = response.getJSONArray("results");
//                    for (int i = 0; i < resultsArray.length(); i++) {
//                    for (int i = 5; i >= 0; i--) {
//                        Log.i("MovieDBHelper", response.getString("");
                    movie = Movie.Factory.NewMovieWithAdditionalFields(response);
                    Log.i("MovieDbHelperTest", movie.getTitle());
                    SetAllViewFields();
                    GetFeaturedCrew(movieId);
                    GetFeaturedCast(movieId);
//                        movies.add(movie);
//                        Log.i("MovieDbHelperTest", String.valueOf(i));
                }//
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("MovieDbHelperTest", String.valueOf(statusCode));
            }
        });
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
        featuredCrewRecyclerView = view.findViewById(R.id.featuredCrewRecyclerView);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        featuredCrewRecyclerView.setLayoutManager(gridLayoutManager1); // set LayoutManager to RecyclerView
        featuredCastRecyclerView = view.findViewById(R.id.featuredCastRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        featuredCastRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


        getMovieDetails(movieId);
        return view;
    }

    private void SetAllViewFields() {
        ratingProgressBar.setProgress((int) movie.getVote_average() * 10);
        txtProgressbar.setText((movie.getVote_average() * 10) + "%");
        overviewTextView.setText(movie.getOverview());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(getActivity());
        GlideApp.with(this)
                .load(DbHelper.IMAGE_BASE_URL + "w500" + movie.getBackdrop_path())
                .placeholder(circularProgressDrawable)
                .into(movieImageView);
//        featuredCrewList.add(new FeaturedCrew("David Leitch", "Director"));
//        featuredCrewList.add(new FeaturedCrew("Paul Wernick", "Screenplay"));
//        featuredCrewList.add(new FeaturedCrew("Ryan Reynolds", "Screenplay"));
//        featuredCrewList.add(new FeaturedCrew("Fabian Nicieza", "Characters"));

//        featuredCastList.add(new FeaturedCast("Ryan Reynolds", "Wade Wilson / Deadpool / Juggernaut / Himself", "http://image.tmdb.org/t/p/w92/h1co81QaT2nJA41Sb7eZwmWl1L2.jpg"));
//        featuredCastList.add(new FeaturedCast("Josh Brolin", "Cable", "http://image.tmdb.org/t/p/w92/x8KKnvHyPvH16M6waAnY1OeCtA8.jpg"));
//        featuredCastList.add(new FeaturedCast("Morena Baccarin", "Vanessa", "http://image.tmdb.org/t/p/w92/dhdQT0fMRcbg8Gi9nx7JF0oVzzr.jpg"));
//        featuredCastList.add(new FeaturedCast("Julian Dennison", "Russel Collins / Firefist", "http://image.tmdb.org/t/p/w92/ApBsNEF9JnXDJ27XLaWnRXdVCQz.jpg"));


    }

    private void GetFeaturedCrew(int movieId) {
        DbHelper movieHelper = new DbHelper();
        movieHelper.get("movie/" + movieId + "/credits?language=en-US&", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("MovieDbHelperTest", "Success");
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray resultsArray = response.getJSONArray("crew");
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

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("MovieDbHelperTest", String.valueOf(statusCode));
            }
        });
    }

    private void GetFeaturedCast(int movieId) {
        DbHelper movieHelper = new DbHelper();
        movieHelper.get("movie/" + movieId + "/credits?language=en-US&", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("MovieDbHelperTest", "Success");
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray resultsArray = response.getJSONArray("cast");
                    for (int i = 0; i < resultsArray.length() && i < 20; i++) {
                        JSONObject jsonObject = resultsArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String character = jsonObject.getString("character");
                        String profile_path = jsonObject.getString("profile_path");
                        FeaturedCast featuredCast = new FeaturedCast(name, character, profile_path);
                        featuredCastList.add(featuredCast);
                    }
                    FeaturedCastAdapter featuredCastAdapter = new FeaturedCastAdapter(featuredCastList);
                    featuredCastRecyclerView.setAdapter(featuredCastAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("MovieDbHelperTest", String.valueOf(statusCode));
            }
        });
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
