// domain/usecases/CurrentUser.java
package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.models.User;
import ru.mirea.golysheva.domain.repository.AuthRepository;

public class CurrentUser {
    private final AuthRepository repo;

    public CurrentUser(AuthRepository repo) { this.repo = repo; }

    public User execute() {
        return repo.currentUser(); // может вернуть null, если не авторизован
    }
}
