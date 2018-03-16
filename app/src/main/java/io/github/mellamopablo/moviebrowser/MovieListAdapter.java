package io.github.mellamopablo.moviebrowser;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.mellamopablo.moviebrowser.model.MovieSummary;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {
    private List<MovieSummary> mDataSet = new ArrayList<>();
    private PublishSubject<MovieSummary> onClickSubject = PublishSubject.create();

    public Observable<MovieSummary> getClicks() {
        return onClickSubject.share();
    }

    public MovieListAdapter(List<MovieSummary> initialDataSet) {
        this.mDataSet.addAll(initialDataSet);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_summary, parent, false);

        return new MovieListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int i) {
        final MovieSummary movie = mDataSet.get(i);

        holder.mSummaryTitle.setText(movie.getName());
        holder.mSummaryPoster.setImageBitmap(movie.getPoster());

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    void addNewData(List<MovieSummary> newData) {
        mDataSet.addAll(newData);
        notifyDataSetChanged();
    }
}
