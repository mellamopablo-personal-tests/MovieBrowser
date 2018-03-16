package io.github.mellamopablo.moviebrowser.support;

import android.content.Context;
import android.widget.Toast;

import io.github.mellamopablo.moviebrowser.R;

public class ErrorHandler {
    public static void handle(Throwable e, Context context) {
        e.printStackTrace();

        Toast
            .makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT)
            .show();
    }
}
