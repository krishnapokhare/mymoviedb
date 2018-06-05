package com.pokhare.mymoviedb.helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ApiHelper {
    private static final String API_KEY = "150bd9f4531ed5941c9ce34a39109b57";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static ApiHelper mInstance;
    private static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private ApiHelper() {
    }

    public static synchronized ApiHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ApiHelper();
        }
        return mInstance;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl + "api_key=" + API_KEY;
    }

    public void GetJsonObject(String url, final ApiCallbackListener listener, Context ctx) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getAbsoluteUrl(url), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onTaskCompleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiHelper", error.getMessage());
            }
        });
        VolleyRequester.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public String GetImageUrl(String relativeUrl, String size) {
        return IMAGE_BASE_URL + size + relativeUrl;
    }

//    class GetConfigurationValuesTask implements ApiCallbackListener{
//
//        @Override
//        public void onTaskCompleted(JSONObject result) {
//            try {
//                Log.i("ImageHelper",result.toString());
//                JSONObject imageObject=result.getJSONObject("images");
//                if(imageObject != null){
//                    IMAGE_BASE_URL=imageObject.getString("base_url");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
