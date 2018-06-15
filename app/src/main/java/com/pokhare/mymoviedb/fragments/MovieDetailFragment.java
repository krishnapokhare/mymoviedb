package com.pokhare.mymoviedb.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.pokhare.mymoviedb.adapters.KeyValueLayoutAdapter;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;
import com.pokhare.mymoviedb.models.FeaturedCrew;
import com.pokhare.mymoviedb.models.Movie;
import com.pokhare.mymoviedb.viewmodels.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MOVIE_ID = "movieId";

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
    private RecyclerView factsRecyclerView;
    private List<List<String>> movieFactsList;
    private KeyValueLayoutAdapter keyValueLayoutAdapter;
    private FeaturedCastAdapter featuredCastAdapter;
    private FeaturedCrewAdapter featuredCrewAdapter;

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
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIE_ID)) {
                movieId = savedInstanceState.getInt(MOVIE_ID);
            }
        }
        MovieDetailsViewModel viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movieParam) {
                movie = movieParam;
                SetAllViewFields();
            }
        });

        viewModel.getMovieFacts().observe(this, new Observer<List<List<String>>>() {
            @Override
            public void onChanged(@Nullable List<List<String>> lists) {
                keyValueLayoutAdapter.setValues(lists);
            }
        });

        viewModel.getFeaturedCast().observe(this, new Observer<List<FeaturedCast>>() {
            @Override
            public void onChanged(@Nullable List<FeaturedCast> featuredCasts) {
                featuredCastList = featuredCasts;
                featuredCastAdapter.setValues(featuredCasts);
            }
        });

        viewModel.getFeaturedCrew().observe(this, new Observer<List<FeaturedCrew>>() {
            @Override
            public void onChanged(@Nullable List<FeaturedCrew> featuredCrews) {
                featuredCrewAdapter.setValues(featuredCrews);
            }
        });
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
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
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

        factsRecyclerView = view.findViewById(R.id.movieFactsRecyclerView);
        movieFactsList = new ArrayList<List<String>>();
        keyValueLayoutAdapter = new KeyValueLayoutAdapter(movieFactsList);
        factsRecyclerView.setAdapter(keyValueLayoutAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        factsRecyclerView.setLayoutManager(linearLayoutManager2);
        featuredCrewAdapter = new FeaturedCrewAdapter(featuredCrewList);
        featuredCrewRecyclerView.setAdapter(featuredCrewAdapter); // set the Adapter to RecyclerView
        featuredCastAdapter = new FeaturedCastAdapter(featuredCastList.size() >= 10 ? featuredCastList.subList(0, 9) : featuredCastList, new CastRecyclerViewAdapter.OnListFragmentInteractionListener() {
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
        return view;
    }

    private void SetAllViewFields() {
        ((MainActivity) getActivity()).setActionBarTitle(movie.getTitle());
        ratingProgressBar.setProgress((int) this.movie.getVote_average() * 10);
        txtProgressbar.setText((this.movie.getVote_average() * 10) + "%");
        overviewTextView.setText(this.movie.getOverview());
        movieTitleTextView.setText(this.movie.getTitle());

        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(getActivity());
        GlideApp.with(this)
                .load(ApiHelper.getInstance().GetImageUrl(this.movie.getBackdrop_path(), "w500"))
                .placeholder(circularProgressDrawable)
                .into(movieImageView);
        GlideApp.with(this)
                .load(ApiHelper.getInstance().GetImageUrl(this.movie.getPoster_path(), "w154"))
                .placeholder(circularProgressDrawable)
                .into(moviePosterImageView);
    }

//    class GetMovieDetailsTask implements ApiCallbackListener {
//
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            Log.i("ApiHelper", "Success");
//            try {
//
//                movie = Movie.Factory.NewMovieWithAdditionalFields(result);
//                Log.i("ApiHelper", movie.getTitle());
//                ((MainActivity) getActivity()).setActionBarTitle(movie.getTitle());
//                GetAllMovieFacts(movie);
//                SetAllViewFields(movie);
//                ApiHelper.getInstance().GetJsonObject("movie/" + movie.getId() + "/credits?language=en-US&",
//                        new GetFeaturedCrewTask(), getContext().getApplicationContext());
//                ApiHelper.getInstance().GetJsonObject("movie/" + movie.getId() + "/credits?language=en-US&",
//                        new GetFeaturedCastTask(), getContext().getApplicationContext());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void GetAllMovieFacts(Movie movie) {
//            movieFactsList.clear();
//            movieFactsList.add(Arrays.asList("Status", movie.getStatus()));
//            movieFactsList.add(Arrays.asList("Original Language", movie.getOriginal_language()));
//            movieFactsList.add(Arrays.asList("Runtime", formatRuntime(movie.getRuntime())));
//            movieFactsList.add(Arrays.asList("Budget", "$" + movie.getBudget()));
//            keyValueLayoutAdapter.notifyDataSetChanged();
//        }
//
//        private String formatRuntime(int runtime) {
//            if (runtime <= 60) {
//                return runtime + "m";
//            } else {
//                int hours = runtime / 60;
//                int mins = runtime % 60;
//                if (mins > 0) {
//                    return hours + "h " + mins + "m";
//                } else {
//                    return hours + "hrs";
//                }
//            }
//        }
//    }
//
//    class GetFeaturedCrewTask implements ApiCallbackListener {
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            Log.i("MovieDbHelperTest", "Success");
//            // If the response is JSONObject instead of expected JSONArray
//            try {
//                featuredCrewList.clear();
//                JSONArray resultsArray = result.getJSONArray("crew");
//                for (int i = 0; i < resultsArray.length() && i < 4; i++) {
//                    JSONObject jsonObject = resultsArray.getJSONObject(i);
//                    String name = jsonObject.getString("name");
//                    String role = jsonObject.getString("job");
//                    FeaturedCrew featuredCrew = new FeaturedCrew(name, role);
//                    featuredCrewList.add(featuredCrew);
//                }
//
//                FeaturedCrewAdapter featuredCrewAdapter = new FeaturedCrewAdapter(featuredCrewList);
//                featuredCrewRecyclerView.setAdapter(featuredCrewAdapter); // set the Adapter to RecyclerView
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class GetFeaturedCastTask implements ApiCallbackListener {
//
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            Log.i("MovieDbHelperTest", "Success");
//            // If the response is JSONObject instead of expected JSONArray
//            try {
//                featuredCastList.clear();
//                JSONArray resultsArray = result.getJSONArray("cast");
//                for (int i = 0; i < resultsArray.length(); i++) {
//                    JSONObject jsonObject = resultsArray.getJSONObject(i);
//                    int id = jsonObject.getInt("id");
//                    String name = jsonObject.getString("name");
//                    String character = jsonObject.getString("character");
//                    String profile_path = jsonObject.getString("profile_path");
//                    FeaturedCast featuredCast = new FeaturedCast(id, name, character, profile_path);
//                    featuredCastList.add(featuredCast);
//                }
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
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
