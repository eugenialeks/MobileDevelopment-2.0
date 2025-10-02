package ru.mirea.golysheva.lesson9.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.golysheva.lesson9.domain.models.Movie;
import ru.mirea.golysheva.lesson9.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String PREFS_NAME = "movie_prefs";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";

    private final SharedPreferences prefs;

    public MovieRepositoryImpl(Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return prefs.edit()
                .putInt(KEY_MOVIE_ID, movie.getId())
                .putString(KEY_MOVIE_NAME, movie.getName())
                .commit();
    }

    @Override
    public Movie getMovie() {
        int id = prefs.getInt(KEY_MOVIE_ID, 1);
        String name = prefs.getString(KEY_MOVIE_NAME, "Бегущий в лабиринте");
        return new Movie(id, name);
    }
}
