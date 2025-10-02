package ru.mirea.golysheva.lesson9.domain.usecases;

import ru.mirea.golysheva.lesson9.domain.models.Movie;
import ru.mirea.golysheva.lesson9.domain.repository.MovieRepository;

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
