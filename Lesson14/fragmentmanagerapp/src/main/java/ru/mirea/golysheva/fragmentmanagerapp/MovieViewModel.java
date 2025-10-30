package ru.mirea.golysheva.fragmentmanagerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    public void selectMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
