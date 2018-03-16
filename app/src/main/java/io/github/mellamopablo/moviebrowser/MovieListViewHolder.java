package io.github.mellamopablo.moviebrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class MovieListViewHolder extends RecyclerView.ViewHolder {
    View mRootView;
    TextView mSummaryTitle;
    ImageView mSummaryPoster;

    MovieListViewHolder(View itemView) {
        super(itemView);

        mRootView = itemView;
        mSummaryTitle = itemView.findViewById(R.id.movie_summary_title);
        mSummaryPoster = itemView.findViewById(R.id.movie_summary_poster);
    }
}
