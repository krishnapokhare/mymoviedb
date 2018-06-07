package com.pokhare.mymoviedb.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.activities.MainActivity;
import com.pokhare.mymoviedb.helpers.ApiCallbackListener;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CAST_ID = "castId";

    // TODO: Rename and change types of parameters
    private int castId;
    private FeaturedCast featuredCast;
    private TextView castNameTextView;
    private ImageView castImageView;
    private TextView castDetailsBiographyTextView;


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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_CAST_ID, castId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        castId = savedInstanceState.getInt(ARG_CAST_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            castId = getArguments().getInt(ARG_CAST_ID);
        }
    }

    private void SetAllViewFields() {
        castNameTextView.setText(featuredCast.getName());
        castDetailsBiographyTextView.setText(featuredCast.getBiography());
        CircularProgressDrawable circularProgressDrawable = Global.getCircularProgressDrawable(getActivity());
        GlideApp.with(this)
                .load(ApiHelper.getInstance().GetImageUrl(featuredCast.getImageUrl(), "w500"))
                .placeholder(circularProgressDrawable)
                .into(castImageView);
    }

    class GetCastDetailsTask implements ApiCallbackListener {

        @Override
        public void onTaskCompleted(JSONObject result) {
            Log.i("ApiHelper", "Success");
            // If the response is JSONObject instead of expected JSONArray
            try {
                featuredCast = FeaturedCast.Factory.NewFeaturedCast(result);
                Log.i("FeaturedCast", featuredCast.toString());
                ((MainActivity) getActivity()).setActionBarTitle(featuredCast.getName());
                SetAllViewFields();
            }//
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cast_details, container, false);
        castNameTextView = view.findViewById(R.id.castDetailsNameTextView);
        castImageView = view.findViewById(R.id.castDetailsImageView);
        castDetailsBiographyTextView = view.findViewById(R.id.castDetailsBiographyTextView);
        ApiHelper.getInstance().GetJsonObject("person/" + castId + "?language=en-US&page=1&"
                , new GetCastDetailsTask(), getContext().getApplicationContext());
        return view;
    }
}
