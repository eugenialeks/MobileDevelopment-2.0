package ru.mirea.golysheva.domain.repository;

import ru.mirea.golysheva.domain.models.User;

public interface AuthRepository {

    interface Callback {
        void onSuccess(User user);
        void onError(Throwable t);
    }

    void login(String email, String password, Callback cb);
    void loginAnonymously(Callback cb);
    void register(String email, String password, Callback cb);
    void logout();

    boolean isLoggedIn();
    User currentUser();   // <— нужен для use-case CurrentUser
}
