package ru.mirea.golysheva.skincare.presentation.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

import ru.mirea.golysheva.domain.models.Tip;
import ru.mirea.golysheva.domain.usecases.GetWeatherTipUseCase;

public class HomeViewModel extends ViewModel {

    private final GetWeatherTipUseCase getTip;

    public final MutableLiveData<String> title       = new MutableLiveData<>("Погода");
    public final MutableLiveData<String> temperature = new MutableLiveData<>("—");
    public final MutableLiveData<String> uvIndex     = new MutableLiveData<>("—");
    public final MutableLiveData<String> advice      = new MutableLiveData<>("Загружаем...");

    public HomeViewModel(GetWeatherTipUseCase getTip) { this.getTip = getTip; }

    public void load(double lat, double lon) {
        new Thread(() -> {
            try {
                Tip t = getTip.execute(lat, lon);

                title.postValue(t.getTitle() != null ? t.getTitle() : "Погода");

                String tempStr = (t.getTempC() == null)
                        ? "—" : String.format(Locale.getDefault(), "%.0f°C", t.getTempC());
                String uvStr = (t.getUvIndex() == null)
                        ? "—" : String.format(Locale.getDefault(), "%.1f", t.getUvIndex());

                temperature.postValue(tempStr);
                uvIndex.postValue(uvStr);

                if (t.getError() != null) advice.postValue(t.getAdvice());
                else advice.postValue(t.getAdvice());

            } catch (Throwable e) {
                Log.e("HomeVM", "weather error", e);
                title.postValue("Погода");
                temperature.postValue("—");
                uvIndex.postValue("—");
                advice.postValue("Не удалось обновить данные. Проверьте сеть.");
            }
        }).start();
    }
}
