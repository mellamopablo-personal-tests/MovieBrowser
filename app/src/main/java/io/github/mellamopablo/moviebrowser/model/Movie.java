package io.github.mellamopablo.moviebrowser.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie extends MovieSummary {
    private String synopsis;
    private List<String> genres;
    private int budget;

    public Movie(
            int id,
            String name,
            Date releaseDate,
            Bitmap poster,
            String synopsis,
            List<String> genres,
            int budget
    ) {
        super(id, name, releaseDate, poster);
        this.synopsis = synopsis;
        this.genres = genres;
        this.budget = budget;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public List<String> getGenres() {
        List<String> copy = new ArrayList<>();
        copy.addAll(genres);
        return copy;
    }

    public int getBudget() {
        return budget;
    }
}
