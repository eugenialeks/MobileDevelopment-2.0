package ru.mirea.golysheva.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;

import ru.mirea.golysheva.data.storage.MovieStorage;
import ru.mirea.golysheva.data.storage.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {
    private static final String PREFS    = "movie_prefs";
    private static final String KEY_ID   = "movie_id";
    private static final String KEY_NAME = "movie_name";
    private static final String KEY_DATE = "movie_date";

    private final SharedPreferences prefs;

    public SharedPrefMovieStorage(Context ctx) {
        this.prefs = ctx.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public Movie get() {
        int id = prefs.getInt(KEY_ID, -1);
        String name = prefs.getString(KEY_NAME, "unknown");
        String date = prefs.getString(KEY_DATE, LocalDate.now().toString());
        return new Movie(id, name, date);
    }

    @Override
    public boolean save(Movie movie) {
        return prefs.edit()
                .putInt(KEY_ID, movie.getId())
                .putString(KEY_NAME, movie.getName())
                .putString(KEY_DATE, LocalDate.now().toString())
                .commit();
    }
}