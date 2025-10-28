package ru.mirea.golysheva.domain.usecases;

import ru.mirea.golysheva.domain.models.Tip;
import ru.mirea.golysheva.domain.repository.WeatherRepository;

public class GetWeatherTipUseCase {
    private final WeatherRepository repo;

    public GetWeatherTipUseCase(WeatherRepository repo) {
        this.repo = repo;
    }

    public Tip execute(double lat, double lon) {
        return repo.getTip(lat, lon);
    }
}
