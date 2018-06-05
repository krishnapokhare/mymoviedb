package com.pokhare.mymoviedb.models;

import android.util.Log;

import com.pokhare.mymoviedb.helpers.Convert;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Movie {
    private int vote_count;
    private int id;
    private boolean video;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids;
    private List<Integer> productionCompanies_ids;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private Date release_date;
    private long budget;
    private String imdb_id;
    private int runtime;
    private String tagline;
    private String status;

    public List<Integer> getProductionCompanies_ids() {
        return productionCompanies_ids;
    }

    public void setProductionCompanies_ids(List<Integer> productionCompanies_ids) {
        this.productionCompanies_ids = productionCompanies_ids;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getRelease_date() {
        return release_date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "vote_count=" + vote_count +
                ", id=" + id +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", genre_ids=" + genre_ids +
                ", productionCompanies_ids=" + productionCompanies_ids +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date=" + release_date +
                ", budget=" + budget +
                ", imdb_id='" + imdb_id + '\'' +
                ", runtime=" + runtime +
                ", tagline='" + tagline + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Factory {
        public static Movie NewMovie(String name) {
            Movie movie = new Movie();
            movie.setTitle(name);
            return movie;
        }

        public static Movie NewMovieWithBasicFields(JSONObject jsonObject) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Movie movie = new Movie();
            Map<String, Object> map = Convert.fromJson(jsonObject);
            try {
                movie.setTitle(jsonObject.getString("title"));
                movie.setAdult(jsonObject.getBoolean("adult"));
                movie.setBackdrop_path(jsonObject.getString("backdrop_path"));
                movie.setGenre_ids((List<Integer>) map.get("genre_ids"));
                movie.setId(jsonObject.getInt("id"));
                movie.setOriginal_language(jsonObject.getString("original_language"));
                movie.setOriginal_title(jsonObject.getString("original_title"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setPopularity(jsonObject.getDouble("popularity"));
                movie.setPoster_path(jsonObject.getString("poster_path"));
                try {
                    movie.setRelease_date(format.parse(jsonObject.getString("release_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                movie.setVideo(jsonObject.getBoolean("video"));
                movie.setVote_average(jsonObject.getDouble("vote_average"));
                movie.setVote_count(jsonObject.getInt("vote_count"));
                Log.i("Movie", movie.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movie;
        }

        public static Movie NewMovieWithAdditionalFields(JSONObject jsonObject) {
            Movie movie = Movie.Factory.NewMovieWithBasicFields(jsonObject);
            Map<String, Object> map = Convert.fromJson(jsonObject);
            try {
                movie.setBudget(jsonObject.getLong("budget"));
                movie.setImdb_id(jsonObject.getString("imdb_id"));
                movie.setProductionCompanies_ids((List<Integer>)map.get("production_companies"));
                movie.setRuntime(jsonObject.getInt("runtime"));
                movie.setStatus(jsonObject.getString("status"));
                movie.setTagline(jsonObject.getString("tagline"));
                Log.i("MovieWithDetails", movie.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movie;
        }

//        public static List<Movie> GetPopularMovies() {
////            List<Movie> shows = new ArrayList<Movie>();
////            shows.add(Movie.Factory.NewMovie("The Big Bang Theory"));
////            shows.add(Movie.Factory.NewMovie("Spartacus"));
////            return shows;
////
////            // Create URL
////            URL githubEndpoint = new URL("https://api.github.com/");
////
////// Create connection
////            HttpsURLConnection myConnection =
////                    (HttpsURLConnection) githubEndpoint.openConnection();
//            DbHelper movieHelper=new DbHelper();
//
//            List<Movie> movies = movieHelper.GetPopularMovies();
//            Log.i("MovieSize",String.valueOf(movies.size()));
//            return movies;
//        }
    }
}
