package ru.mirea.golysheva.data.storage.network.dto;

public class WeatherResponse {
    public Current current;
    public static class Current {
        public String time;
        public Double temperature_2m;
        public Double uv_index;
    }
}