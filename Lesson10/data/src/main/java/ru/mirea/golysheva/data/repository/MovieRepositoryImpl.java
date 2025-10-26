package ru.mirea.golysheva.data.repository;

import java.time.LocalDate;

import ru.mirea.golysheva.domain.models.Movie;                // DOMAIN
import ru.mirea.golysheva.domain.repository.MovieRepository;

import ru.mirea.golysheva.data.storage.MovieStorage;
import ru.mirea.golysheva.data.storage.models.*;              // DATA

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage storage;

    public MovieRepositoryImpl(MovieStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        // domain -> data
        ru.mirea.golysheva.data.storage.models.Movie dto =
                new ru.mirea.golysheva.data.storage.models.Movie(
                        movie.getId(),
                        movie.getName(),
                        LocalDate.now().toString()
                );
        return storage.save(dto);
    }

    @Override
    public Movie getMovie() {
        ru.mirea.golysheva.data.storage.models.Movie dto = storage.get();
        return new Movie(dto.getId(), dto.getName());
    }
}
