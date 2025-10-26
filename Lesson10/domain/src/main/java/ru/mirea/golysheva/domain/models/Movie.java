package ru.mirea.golysheva.domain.models;

public class Movie {
    private final int id;
    private final String name;

    public Movie(int id, String name) {
        this.id = id;
        this.name = name == null ? "" : name.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }
}