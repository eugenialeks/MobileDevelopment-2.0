// domain/usecases/LogoutUser.java
package ru.mirea.golysheva.domain.usecases.auth;

import ru.mirea.golysheva.domain.repository.AuthRepository;

public class LogoutUser {
    private final AuthRepository repo;

    public LogoutUser(AuthRepository repo) {
        this.repo = repo;
    }

    public void execute() {
        repo.logout();
    }
}
