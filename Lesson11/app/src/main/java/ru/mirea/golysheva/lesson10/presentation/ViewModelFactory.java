package ru.mirea.golysheva.lesson10.presentation;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.MovieRepositoryImpl;
import ru.mirea.golysheva.data.storage.MovieStorage;
import ru.mirea.golysheva.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.golysheva.domain.repository.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static final String TAG = "ViewModelFactory";
    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
        Log.d(TAG, "ViewModelFactory создана");
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.d(TAG, "Создание ViewModel для класса: " + modelClass.getSimpleName());

        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            MovieStorage storage = new SharedPrefMovieStorage(context);
            MovieRepository repository = new MovieRepositoryImpl(storage);
            Log.d(TAG, "Все зависимости созданы успешно");
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}