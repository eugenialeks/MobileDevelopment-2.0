package ru.mirea.golysheva.lesson10.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import ru.mirea.golysheva.domain.models.Movie;
import ru.mirea.golysheva.domain.repository.MovieRepository;
import ru.mirea.golysheva.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.golysheva.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MovieRepository movieRepository;

    private final MutableLiveData<String> movieTextLiveData = new MutableLiveData<>();

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        Log.d(TAG, "MainViewModel создан");
    }

    public MutableLiveData<String> getMovieTextLiveData() {
        return movieTextLiveData;
    }

    public void saveMovie(String movieName) {
        Log.d(TAG, "saveMovie вызван с названием: " + movieName);

        Movie movie = new Movie(2, movieName);
        boolean isSaved = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        if (isSaved) {
            movieTextLiveData.setValue("Сохранено");
            Log.d(TAG, "Фильм успешно сохранен");
        } else {
            movieTextLiveData.setValue("Ошибка: пустое имя");
            Log.d(TAG, "Ошибка сохранения: пустое имя");
        }
    }

    public void getMovie() {
        Log.d(TAG, "getMovie вызван");

        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        if (movie.getName().isEmpty()) {
            movieTextLiveData.setValue("Нет данных!");
            Log.d(TAG, "Получен пустой фильм");
        } else {
            String movieText = "Любимый фильм: " + movie.getName();
            movieTextLiveData.setValue(movieText);
            Log.d(TAG, "Отображен фильм: " + movie.getName());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "MainViewModel уничтожен");
    }
}