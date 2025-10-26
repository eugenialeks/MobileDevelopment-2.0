package ru.mirea.golysheva.data.storage;

import ru.mirea.golysheva.data.storage.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}