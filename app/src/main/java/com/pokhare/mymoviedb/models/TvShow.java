package com.pokhare.mymoviedb.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pokhare.mymoviedb.helpers.Convert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class TvShow {
    private String original_name;
    private List<Integer> genre_ids;
    private String name;
    private double popularity;
    private List<String> origin_country;
    private int vote_count;
    private String first_air_date;
    private String backdrop_path;
    private String original_language;
    private int id;
    private double vote_average;
    private String overview;
    private String poster_path;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public static class Factory {
        public static TvShow NewTvShow(JSONObject jsonObject) {
            TvShow show = new TvShow();
            Map<String, Object> map = Convert.fromJson(jsonObject);
            try {
                show.setName(jsonObject.getString("name"));
                show.setFirst_air_date(jsonObject.getString("first_air_date"));
                show.setBackdrop_path(jsonObject.getString("backdrop_path"));
                show.setGenre_ids((List<Integer>)map.get("genre_ids"));
                show.setId(jsonObject.getInt("id"));
                show.setOriginal_language(jsonObject.getString("original_language"));
                show.setOriginal_name(jsonObject.getString("original_name"));
                show.setOverview(jsonObject.getString("overview"));
                show.setPopularity(jsonObject.getDouble("popularity"));
                show.setPoster_path(jsonObject.getString("poster_path"));
                show.setOrigin_country((List<String>)map.get("origin_country"));
                show.setVote_average(jsonObject.getDouble("vote_average"));
                show.setVote_count(jsonObject.getInt("vote_count"));
                Log.i("TVShow",show.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return show;
        }

        public static List<TvShow> GetPopularTvShows() {
            List<TvShow> shows = new ArrayList<TvShow>();
//            shows.add(Factory.NewTvShow("The Big Bang Theory"));
//            shows.add(Factory.NewTvShow("Spartacus"));
            return shows;
        }
    }
}
