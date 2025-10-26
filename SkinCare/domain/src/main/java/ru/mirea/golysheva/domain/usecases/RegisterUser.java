// domain/usecases/RegisterUser.java
package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.repository.AuthRepository;

public class RegisterUser {
    private final AuthRepository repo;

    public RegisterUser(AuthRepository repo) {
        this.repo = repo;
    }

    public void execute(String email, String password, AuthRepository.Callback cb) {
        repo.register(email, password, cb);
    }
}
