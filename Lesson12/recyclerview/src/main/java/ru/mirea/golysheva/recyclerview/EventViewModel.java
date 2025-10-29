package ru.mirea.golysheva.recyclerview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {

    private final MutableLiveData<List<HistoricalEvent>> eventsLiveData = new MutableLiveData<>();

    public LiveData<List<HistoricalEvent>> getEvents() {
        return eventsLiveData;
    }


    public void loadHistoricalEvents() {
        ArrayList<HistoricalEvent> eventList = new ArrayList<>();
        eventList.add(new HistoricalEvent("Крещение Руси", "Принятие христианства как государственной религии в 988 году.", R.drawable.event_baptism_rus));
        eventList.add(new HistoricalEvent("Ледовое побоище", "Битва русского войска под предводительством Александра Невского с ливонскими рыцарями на Чудском озере.", R.drawable.event_ice_battle));
        eventList.add(new HistoricalEvent("Основание Санкт-Петербурга", "Город основан Петром I в 1703 году, ставший новой столицей России.", R.drawable.event_spb_founding));
        eventList.add(new HistoricalEvent("Бородинское сражение", "Крупнейшее сражение Отечественной войны 1812 года.", R.drawable.event_borodino));
        eventList.add(new HistoricalEvent("Первый полет в космос", "Юрий Гагарин стал первым человеком, совершившим полет в космическое пространство 12 апреля 1961 года.", R.drawable.event_gagarin_flight));

        eventsLiveData.setValue(eventList);
    }
}