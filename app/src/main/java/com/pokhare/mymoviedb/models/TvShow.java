package com.pokhare.mymoviedb.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TvShow {
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
        public static TvShow NewTvShow(String name) {
            TvShow show = new TvShow();
            show.setName(name);
            return show;
        }

        public static List<TvShow> GetPopularTvShows() {
            List<TvShow> shows = new ArrayList<TvShow>();
            shows.add(Factory.NewTvShow("The Big Bang Theory"));
            shows.add(Factory.NewTvShow("Spartacus"));
            return shows;
        }
    }
}
