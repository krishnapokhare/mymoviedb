package com.pokhare.mymoviedb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String original_name;
    String name;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Factory {
        public static Movie NewMovie(String name) {
            Movie movie = new Movie();
            movie.setName(name);
            return movie;
        }

        public static List<Movie> GetPopularMovies() {
            List<Movie> shows = new ArrayList<Movie>();
            shows.add(Movie.Factory.NewMovie("The Big Bang Theory"));
            shows.add(Movie.Factory.NewMovie("Spartacus"));
            return shows;
        }
    }
}
