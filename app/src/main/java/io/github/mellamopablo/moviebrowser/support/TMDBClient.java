package io.github.mellamopablo.moviebrowser.support;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mellamopablo.moviebrowser.model.Movie;
import io.github.mellamopablo.moviebrowser.model.MovieSummary;

public abstract class TMDBClient {
    private static final String API_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "TMDB_API_KEY_HERE";

    private static final String POSTERS_URL = "https://image.tmdb.org/t/p";
    private static final String POSTERS_SMALL = "w185_and_h278_bestv2";
    private static final String POSTERS_BIG = "w600_and_h900_bestv2";

    @SuppressLint("SimpleDateFormat")
    public static List<MovieSummary> getPopularMovies() throws RequestException {
        try {
            List<MovieSummary> movies = new ArrayList<>();
            JSONObject json = TMDBClient.request(
                    "discover/movie",
                    Collections.singletonList(
                            Pair.create("sorty_by", "popularity.desc")
                    )
            );
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject rawMovie = results.getJSONObject(i);
                movies.add(new MovieSummary(
                        rawMovie.getInt("id"),
                        rawMovie.getString("title"),
                        new SimpleDateFormat("YYYY-MM-DD").parse(rawMovie.getString("release_date")),
                        TMDBClient.getBitmap(rawMovie.getString("poster_path"), false)
                ));
            }

            return movies;
        } catch (JSONException | ParseException e) {
            throw new RequestException(e);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Movie getMovie(int id) throws RequestException {
        try {
            JSONObject rawMovie = TMDBClient.request("movie/" + id);

            JSONArray rawGenres = rawMovie.getJSONArray("genres");
            List<String> genres = new ArrayList<>();

            for (int i = 0; i < rawGenres.length(); i++) {
                JSONObject genre = rawGenres.getJSONObject(i);
                genres.add(genre.getString("name"));
            }

            return new Movie(
                    rawMovie.getInt("id"),
                    rawMovie.getString("title"),
                    new SimpleDateFormat("YYYY-MM-DD").parse(rawMovie.getString("release_date")),
                    TMDBClient.getBitmap(rawMovie.getString("poster_path"), true),
                    rawMovie.getString("overview"),
                    genres,
                    rawMovie.getInt("budget")
            );
        } catch (JSONException | ParseException e) {
            throw new RequestException(e);
        }
    }

    private static JSONObject request(String path) throws RequestException {
        return TMDBClient.request(path, Collections.<Pair<String,String>>emptyList());
    }

    private static JSONObject request(
        String path,
        List<Pair<String, String>> queryString
    ) throws RequestException {
        try {
            StringBuilder qs = new StringBuilder();

            // Clonamos para no modificar la lista que nos pasen.
            List<Pair<String, String>> ourQs = new ArrayList<>();
            ourQs.addAll(queryString);
            ourQs.add(Pair.create("api_key", API_KEY));

            for (Pair<String, String> pair: ourQs) {
                String key = pair.first;
                String value = pair.second;

                qs
                    .append(qs.toString().equals("") ? "?" : "&")
                    .append(key)
                    .append("=")
                    .append(value);
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(
                    String.format("%s/%s%s", API_URL, path, qs)
            )
                    .openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            InputStream stream = conn.getInputStream();

            if (stream == null) {
                throw new RequestException("El stream es nulo!");
            }

            StringBuilder response = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            while((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }

            return new JSONObject(response.toString());
        } catch (IOException | JSONException e) {
            throw new RequestException(e);
        }
    }

    private static Bitmap getBitmap(String path, boolean isBig) throws RequestException {
        try {
            return BitmapFactory.decodeStream(
                    new URL(
                            String.format(
                                    "%s/%s%s",
                                    POSTERS_URL,
                                    isBig ? POSTERS_BIG : POSTERS_SMALL,
                                    path
                            )
                    ).openStream()
            );
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public static class RequestException extends IOException {
        RequestException(String message) {
            super(message);
        }

        RequestException(Throwable cause) {
            super(cause);
        }
    }
}
