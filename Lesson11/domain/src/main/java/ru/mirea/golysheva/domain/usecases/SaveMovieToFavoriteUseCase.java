package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.models.Movie;
import ru.mirea.golysheva.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private final MovieRepository repository;

    public SaveMovieToFavoriteUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Movie movie) {
        if (movie == null || movie.getName().isEmpty()) return false;
        return repository.saveMovie(movie);
    }
}