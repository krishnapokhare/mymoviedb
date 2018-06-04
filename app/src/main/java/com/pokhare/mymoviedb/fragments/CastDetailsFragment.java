package com.pokhare.mymoviedb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.activities.MainActivity;
import com.pokhare.mymoviedb.helpers.DbHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CAST_ID = "movieId";

    // TODO: Rename and change types of parameters
    private int castId;
    private FeaturedCast featuredCast;
    private TextView castNameTextView;
    private ImageView castImageView;


    public CastDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CastDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CastDetailsFragment newInstance(int param1) {
        CastDetailsFragment fragment = new CastDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CAST_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            castId = getArguments().getInt(ARG_CAST_ID);
            getCastDetails(castId);
        }
    }

    private void getCastDetails(int castId) {
        Log.i("MovieDBHelper", "method:getCastDetails");
        DbHelper movieHelper = new DbHelper();
        movieHelper.get("person/" + castId + "?language=en-US&page=1&", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("MovieDbHelperTest", "Success");
                // If the response is JSONObject instead of expected JSONArray
                try {
                    featuredCast = FeaturedCast.Factory.NewFeaturedCast(response);
                    Log.i("FeaturedCast", featuredCast.toString());
                    ((MainActivity) getActivity()).setActionBarTitle(featuredCast.getName());
                    SetAllViewFields();
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

    private void SetAllViewFields() {
        castNameTextView.setText(featuredCast.getName());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(getActivity());
        GlideApp.with(this)
                .load(DbHelper.IMAGE_BASE_URL + "w500" + featuredCast.getImageUrl())
                .placeholder(circularProgressDrawable)
                .into(castImageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cast_details, container, false);
        castNameTextView = view.findViewById(R.id.castDetailsNameTextView);
        castImageView = view.findViewById(R.id.castDetailsImageView);
        return view;
    }
}
