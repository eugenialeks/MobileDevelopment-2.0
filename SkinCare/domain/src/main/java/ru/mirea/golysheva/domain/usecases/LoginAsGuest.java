package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.repository.AuthRepository;

public class LoginAsGuest {
    private final AuthRepository repo;

    public LoginAsGuest(AuthRepository repo) {
        this.repo = repo;
    }

    public void execute(AuthRepository.Callback cb) {
        repo.loginAnonymously(cb);
    }
}