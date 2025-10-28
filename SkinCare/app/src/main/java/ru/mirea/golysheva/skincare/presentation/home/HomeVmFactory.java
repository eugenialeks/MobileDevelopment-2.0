package ru.mirea.golysheva.skincare.presentation.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.OpenMeteoRepository;
import ru.mirea.golysheva.domain.repository.WeatherRepository;
import ru.mirea.golysheva.domain.usecases.GetWeatherTipUseCase;

public class HomeVmFactory implements ViewModelProvider.Factory {
    @NonNull @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        WeatherRepository repo = new OpenMeteoRepository();
        GetWeatherTipUseCase uc = new GetWeatherTipUseCase(repo);
        return (T) new HomeViewModel(uc);
    }
}
