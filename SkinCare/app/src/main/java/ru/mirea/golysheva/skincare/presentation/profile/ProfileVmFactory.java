package ru.mirea.golysheva.skincare.presentation.profile;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.AuthRepositoryImpl;
import ru.mirea.golysheva.data.storage.sharedprefs.ClientPrefs;
import ru.mirea.golysheva.domain.repository.AuthRepository;

public class ProfileVmFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ProfileVmFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            AuthRepository repo = new AuthRepositoryImpl(new ClientPrefs(context));
            return (T) new ProfileViewModel(repo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
