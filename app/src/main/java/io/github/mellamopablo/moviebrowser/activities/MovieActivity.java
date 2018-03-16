package io.github.mellamopablo.moviebrowser.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.github.mellamopablo.moviebrowser.R;
import io.github.mellamopablo.moviebrowser.model.Movie;
import io.github.mellamopablo.moviebrowser.support.TMDBClient;

@SuppressWarnings("FieldCanBeLocal")
public class MovieActivity extends AppCompatActivity {
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieGenres;
    private TextView mMovieSynopsis;
    private TextView mMovieBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = this.getIntent();
        mMovieTitle = findViewById(R.id.movieDetailTitle);
        mMoviePoster = findViewById(R.id.movieDetailPoster);
        mMovieGenres = findViewById(R.id.movieDetailGenres);
        mMovieSynopsis = findViewById(R.id.movieDetailSynopsis);
        mMovieBudget = findViewById(R.id.movieDetailsBudget);


        int movieId = intent.getIntExtra("movie_id", 0);
        try {
            Movie movie = new FetchMovie(movieId).execute().get();

            if (movie == null) {
                throw new RuntimeException("La película es null!");
            }

            mMovieTitle.setText(String.format(
                    "%s (%s)",
                    movie.getName(),
                    movie.getReleaseDate().get(Calendar.YEAR)
            ));
            mMoviePoster.setImageBitmap(movie.getPoster());

            StringBuilder genres = new StringBuilder();

            for (int i = 0; i < movie.getGenres().size(); i++) {
                String genre = movie.getGenres().get(i);
                genres.append(genre);

                if (i != movie.getGenres().size() - 1) {
                    genres.append(", ");
                }
            }

            mMovieGenres.setText(genres);
            mMovieSynopsis.setText(movie.getSynopsis());
            mMovieBudget.setText(String.format(
                    "Presupuesto: (%s)",
                    NumberFormat.getCurrencyInstance(Locale.US).format(movie.getBudget())
            ));
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class FetchMovie extends AsyncTask<String, Integer, Movie> {
        private int movieId;

        FetchMovie(int movieId) {
            this.movieId = movieId;
        }

        @Override
        protected @Nullable Movie doInBackground(String ...args) {
            try {
                return TMDBClient.getMovie(this.movieId);
            } catch (TMDBClient.RequestException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
