package ru.mirea.golysheva.domain.repository;

import ru.mirea.golysheva.domain.models.Tip;

public interface WeatherRepository {
    Tip getTip(double lat, double lon);
}
