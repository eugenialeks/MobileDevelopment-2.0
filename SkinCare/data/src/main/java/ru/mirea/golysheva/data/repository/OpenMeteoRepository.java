package ru.mirea.golysheva.data.repository;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.mirea.golysheva.domain.models.Tip;
import ru.mirea.golysheva.domain.repository.WeatherRepository;

public class OpenMeteoRepository implements WeatherRepository {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Tip getTip(double lat, double lon) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
                "&longitude=" + lon +
                "&current=temperature_2m,uv_index&timezone=auto";

        Request req = new Request.Builder().url(url).build();

        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful() || resp.body() == null) {
                return new Tip("Погода", null, null, null,
                        "Не удалось обновить данные. Код: " + resp.code(), "HTTP");
            }

            String json = resp.body().string();
            JSONObject root = new JSONObject(json);
            String city = root.optString("timezone", "Ваш регион");

            JSONObject current = root.getJSONObject("current");
            Double t  = current.has("temperature_2m") ? current.getDouble("temperature_2m") : null;
            Double uv = current.has("uv_index")       ? current.getDouble("uv_index")       : null;

            String advice = buildAdvice(uv, t);

            return new Tip("Погода (" + city + ")", city, t, uv, advice, null);

        } catch (Exception e) {
            return new Tip("Погода", null, null, null,
                    "Не удалось обновить данные. Проверьте сеть.", e.getMessage());
        }
    }

    private String buildAdvice(Double uv, Double t) {
        String uvAdvice;
        if (uv == null) uvAdvice = "Нет данных по УФ.";
        else if (uv < 3) uvAdvice = "Низкий УФ — базовой защиты обычно достаточно.";
        else if (uv < 6) uvAdvice = "Средний УФ — используйте SPF 30+.";
        else             uvAdvice = "Высокий УФ — SPF 50+, избегайте солнца в полдень.";

        String tempAdvice = "";
        if (t != null) {
            if (t < 0)      tempAdvice = " Холодно — добавьте защитный крем.";
            else if (t < 15)tempAdvice = " Прохладно — пригодится хороший увлажнитель.";
            else if (t > 28)tempAdvice = " Жара — лёгкие текстуры и больше воды.";
        }
        return uvAdvice + tempAdvice;
    }
}
