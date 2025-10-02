package ru.mirea.golysheva.skincare.data.repository;

import ru.mirea.golysheva.skincare.domain.models.User;
import ru.mirea.golysheva.skincare.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private User current;

    @Override
    public User login(String email, String password) {
        current = new User("u1", email, "fake_token_123");
        return current;
    }

    @Override
    public void logout() { current = null; }
}
