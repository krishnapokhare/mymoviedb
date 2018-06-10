package com.pokhare.mymoviedb.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokhare.mymoviedb.R;
import com.pokhare.mymoviedb.activities.MainActivity;
import com.pokhare.mymoviedb.adapters.KeyValueLayoutAdapter;
import com.pokhare.mymoviedb.helpers.ApiCallbackListener;
import com.pokhare.mymoviedb.helpers.ApiHelper;
import com.pokhare.mymoviedb.helpers.GlideApp;
import com.pokhare.mymoviedb.helpers.Global;
import com.pokhare.mymoviedb.models.FeaturedCast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastDetailsFragment extends Fragment {
    private static final String ARG_CAST_ID = "castId";

    private int castId;
    private FeaturedCast featuredCast;
    private TextView castNameTextView;
    private ImageView castImageView;
    private TextView castDetailsBiographyTextView;
    private RecyclerView personalDetailsRecycleView;
    private List<List<String>> castPersonalDetailsList;
    private KeyValueLayoutAdapter keyValueLayoutAdapter;


    public CastDetailsFragment() {
        // Required empty public constructor
    }

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
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_CAST_ID)) {
                castId = savedInstanceState.getInt(ARG_CAST_ID);
            }
        }
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
        keyValueLayoutAdapter.notifyDataSetChanged();
    }

    private void GetAllPersonalDetails(FeaturedCast featuredCast) {
//        castPersonalDetailsList.add(Arrays.asList("Known For", "Acting"));
        castPersonalDetailsList.clear();
        castPersonalDetailsList.add(Arrays.asList("Gender", featuredCast.getGender() == 1 ? "Female" : "Male"));
//        castPersonalDetailsList.add(Arrays.asList("Known Credits", "1"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        castPersonalDetailsList.add(Arrays.asList("Birthday", df.format(featuredCast.getBirthDay())));
        castPersonalDetailsList.add(Arrays.asList("Place of Birth", featuredCast.getBirthPlace() != "null" ? featuredCast.getBirthPlace() : "-"));
        castPersonalDetailsList.add(Arrays.asList("Official Site", featuredCast.getHomepage() != "null" ? featuredCast.getHomepage() : "-"));
        castPersonalDetailsList.add(Arrays.asList("Also Known As", featuredCast.getAlsoKnownAs() != "null" ? featuredCast.getAlsoKnownAs() : "-"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cast_details, container, false);
        castNameTextView = view.findViewById(R.id.castDetailsNameTextView);
        castImageView = view.findViewById(R.id.castDetailsImageView);
        castDetailsBiographyTextView = view.findViewById(R.id.castDetailsBiographyTextView);
        personalDetailsRecycleView = view.findViewById(R.id.castPersonalDetailsRecyclerView);
        castPersonalDetailsList = new ArrayList<List<String>>();
        keyValueLayoutAdapter = new KeyValueLayoutAdapter(castPersonalDetailsList);
        personalDetailsRecycleView.setAdapter(keyValueLayoutAdapter);
//        personalDetailsRecycleView.setHasFixedSize(true);
//        personalDetailsRecycleView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        personalDetailsRecycleView.setLayoutManager(linearLayoutManager);
        ApiHelper.getInstance().GetJsonObject("person/" + castId + "?language=en-US&page=1&"
                , new GetCastDetailsTask(), getContext().getApplicationContext());
        return view;
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
                GetAllPersonalDetails(featuredCast);
                SetAllViewFields();
            }//
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
