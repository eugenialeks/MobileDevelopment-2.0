// domain/usecases/IsLoggedIn.java
package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.repository.AuthRepository;

public class IsLoggedIn {
    private final AuthRepository repo;

    public IsLoggedIn(AuthRepository repo) {
        this.repo = repo;
    }

    public boolean execute() {
        return repo.isLoggedIn();
    }
}
