package ru.mirea.golysheva.domain.usecases.auth;

import ru.mirea.golysheva.domain.models.User;
import ru.mirea.golysheva.domain.repository.AuthRepository;

public class LoginUser {
    private final AuthRepository repo;
    public LoginUser(AuthRepository repo) { this.repo = repo; }

    // уже есть — асинхронный
    public void execute(String email, String password, AuthRepository.Callback cb) {
        repo.login(email, password, cb);
    }
}
