package ru.mirea.golysheva.skincare.domain.repository;


import ru.mirea.golysheva.skincare.domain.models.User;

public interface AuthRepository {
    User login(String email, String password);
    void logout();
}
