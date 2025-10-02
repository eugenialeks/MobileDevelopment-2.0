package ru.mirea.golysheva.lesson9.domain.usecases;

import ru.mirea.golysheva.lesson9.domain.models.Movie;
import ru.mirea.golysheva.lesson9.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private final MovieRepository repository;

    public GetFavoriteFilmUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie execute() {
        return repository.getMovie();
    }
}