package io.github.mellamopablo.moviebrowser.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.github.mellamopablo.moviebrowser.MovieListAdapter;
import io.github.mellamopablo.moviebrowser.R;
import io.github.mellamopablo.moviebrowser.model.MovieSummary;
import io.github.mellamopablo.moviebrowser.support.Store;
import io.reactivex.functions.Consumer;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MovieListAdapter(Store.getInstance().getMovies());
        mRecyclerView = findViewById(R.id.movie_list_recycle_view);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.getClicks()
                .subscribe(new Consumer<MovieSummary>() {
                    @Override
                    public void accept(MovieSummary movie) {
                        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                        intent.putExtra("movie_id", movie.getId());

                        MainActivity.this.startActivity(intent);
                    }
                });
    }
}
