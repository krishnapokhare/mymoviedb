package com.pokhare.mymoviedb.helpers;

import android.util.JsonReader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pokhare.mymoviedb.models.Movie;

import org.json.JSONObject;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;

public class DbHelper {
    private static String API_KEY;
    private String HOSTHEADER = "https://api.themoviedb.org/3/";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static String IMAGE_BASE_URL="";
    public static String SECURE_IMAGE_BASE_URL="";

//    public static void DbHelper(){
//        APIKEY = "150bd9f4531ed5941c9ce34a39109b57";
//    }

    public DbHelper(){
        API_KEY = "150bd9f4531ed5941c9ce34a39109b57";
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.i("URL",getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void GetImageBaseUrl() {
        get("configuration?",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                try {
                    Log.i("ImageHelper",response.toString());
                    JSONObject imageObject=response.getJSONObject("images");
                    if(imageObject != null){
                        IMAGE_BASE_URL=imageObject.getString("base_url");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("ImageHelperError",String.valueOf(statusCode));
            }
        });
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl+"api_key="+API_KEY;
    }
}
