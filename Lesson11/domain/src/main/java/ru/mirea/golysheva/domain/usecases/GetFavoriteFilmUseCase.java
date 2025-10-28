package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.models.Movie;
import ru.mirea.golysheva.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private final MovieRepository repository;

    public GetFavoriteFilmUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie execute() {
        return repository.getMovie();
    }
}