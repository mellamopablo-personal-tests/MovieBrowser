package io.github.mellamopablo.moviebrowser.model;

import android.graphics.Bitmap;

import java.util.Calendar;
import java.util.Date;

public class MovieSummary {
    private int id;
    private String name;
    private Date releaseDate;
    private Bitmap poster;

    public MovieSummary(int id, String name, Date releaseDate, Bitmap poster) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Calendar getReleaseDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.releaseDate);
        return c;
    }

    public Bitmap getPoster() {
        return poster;
    }
}
