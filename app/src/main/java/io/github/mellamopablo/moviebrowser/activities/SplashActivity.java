package io.github.mellamopablo.moviebrowser.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.mellamopablo.moviebrowser.model.MovieSummary;
import io.github.mellamopablo.moviebrowser.support.ErrorHandler;
import io.github.mellamopablo.moviebrowser.support.Store;
import io.github.mellamopablo.moviebrowser.support.TMDBClient;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Intent(this, MainActivity.class);

        try {
            Store.getInstance().setMovies(new FetchMovies().execute().get());
            this.startActivity(new Intent(this, MainActivity.class));
        } catch (InterruptedException | ExecutionException e) {
            ErrorHandler.handle(e, this);
        }
    }

    private static class FetchMovies extends AsyncTask<String, Integer, List<MovieSummary>> {
        @Override
        protected List<MovieSummary> doInBackground(String ...args) {
            try {
                return TMDBClient.getPopularMovies();
            } catch (TMDBClient.RequestException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
    }
}
