package ru.mirea.golysheva.lesson9.domain.repository;
import ru.mirea.golysheva.lesson9.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}