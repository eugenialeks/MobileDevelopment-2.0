package ru.mirea.golysheva.fragmentmanagerapp;

public class Movie {
    private final String title;
    private final String director;
    private final int year;

    public Movie(String title, String director, int year) {
        this.title = title;
        this.director = director;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return title;
    }
}
