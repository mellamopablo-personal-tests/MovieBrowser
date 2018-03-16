package io.github.mellamopablo.moviebrowser.support;

import java.util.Collections;
import java.util.List;

import io.github.mellamopablo.moviebrowser.model.MovieSummary;

public class Store {
    private static final Store ourInstance = new Store();
    public static Store getInstance() {
        return ourInstance;
    }
    private Store() {}

    private List<MovieSummary> movies = Collections.emptyList();

    public List<MovieSummary> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSummary> movies) {
        this.movies = movies;
    }
}
