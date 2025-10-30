package ru.mirea.golysheva.skincare.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.golysheva.domain.models.User;
import ru.mirea.golysheva.domain.repository.AuthRepository;
import ru.mirea.golysheva.domain.usecases.auth.LogoutUser;
import ru.mirea.golysheva.domain.usecases.CurrentUser;

public class ProfileViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final LogoutUser logoutUser;
    private final CurrentUser currentUser;

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    public final LiveData<User> user = _user;

    private final MutableLiveData<Boolean> _logoutEvent = new MutableLiveData<>(false);
    public final LiveData<Boolean> logoutEvent = _logoutEvent;

    public ProfileViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        this.logoutUser = new LogoutUser(authRepository);
        this.currentUser = new CurrentUser(authRepository);
    }

    public void loadUserData() {
        User user = currentUser.execute();
        _user.setValue(user);
    }

    public void logout() {
        logoutUser.execute();
        _logoutEvent.setValue(true);
    }

    public void onLogoutEventHandled() {
        _logoutEvent.setValue(false);
    }
}
