//package com.pokhare.mymoviedb.fragments;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.pokhare.mymoviedb.R;
//import com.pokhare.mymoviedb.adapters.TvShowsAdapter;
//import com.pokhare.mymoviedb.helpers.DbHelper;
//import com.pokhare.mymoviedb.models.TvShow;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//
//import cz.msebera.android.httpclient.Header;
//
//import static com.pokhare.mymoviedb.activities.MainActivity.LOG_TAG;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class TvShowFragment extends Fragment {
//
//
//    private List<TvShow> tvShows;
//    TvShowsAdapter tvShowsAdapter;
//    public TvShowFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        Log.i(LOG_TAG, "onCreate called");
//        super.onCreate(savedInstanceState);
//        tvShows=TvShow.Factory.GetPopularTvShows();
//        DbHelper helper=new DbHelper();
//        helper.GetImageBaseUrl();
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_tvshow, container, false);
//        RecyclerView popularTvShowsRecyclerView = view.findViewById(R.id.recyclerView_popularTvShows);
//        if (popularTvShowsRecyclerView == null) {
//            Log.i(LOG_TAG, "RecyclerView is null");
//        }
//        popularTvShowsRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
//        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        if (tvShows.size() > 0 & popularTvShowsRecyclerView != null) {
//            popularTvShowsRecyclerView.setAdapter(new TvShowsAdapter(tvShows));
//        }
//        popularTvShowsRecyclerView.setLayoutManager(MyLayoutManager);
//
//        tvShowsAdapter = new TvShowsAdapter(tvShows);
//        popularTvShowsRecyclerView.setAdapter(tvShowsAdapter);
//        GetPopularTvShows();
//        return view;
//    }
//
//    public void GetPopularTvShows() {
//        Log.i("DBHelper", "method:GetPopularTVShows");
//        DbHelper dbHelper=new DbHelper();
//        dbHelper.get("tv/popular?language=en-US&page=1&", null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                try {
//                    JSONArray resultsArray = response.getJSONArray("results");
//                    for (int i = 0; i < resultsArray.length(); i++) {
//                        Log.i("DBHelper", resultsArray.getJSONObject(i).getString("name"));
//                        TvShow tvShow = TvShow.Factory.NewTvShow(resultsArray.getJSONObject(i));
//                        Log.i("DbHelperTest", tvShow.getName());
//
//                        tvShows.add(tvShow);
//                        Log.i("DbHelperTest", String.valueOf(tvShows.size()));
//                    }
////
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvShowsAdapter.notifyDataSetChanged();
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Log.i("MovieDbHelperTest", String.valueOf(statusCode));
//            }
//        });
//    }
//
//}
