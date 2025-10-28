package ru.mirea.golysheva.domain.repository;
import ru.mirea.golysheva.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}