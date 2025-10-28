package ru.mirea.golysheva.domain.models;

public class User {
    private final String email;
    private final String token;
    private final String name;

    public User(String email, String token, String name) {
        this.email = email;
        this.token = token;
        this.name  = name;
    }

    public String getEmail() { return email; }
    public String getToken() { return token; }
    public String getName()  { return name; }
}
