package ru.mirea.golysheva.domain.models;

public class Tip {
    private final String title;
    private final String city;
    private final Double tempC;
    private final Double uvIndex;
    private final String advice;
    private final String error;

    public Tip(String title, String city, Double tempC, Double uvIndex, String advice, String error) {
        this.title = title;
        this.city = city;
        this.tempC = tempC;
        this.uvIndex = uvIndex;
        this.advice = advice;
        this.error = error;
    }

    public String getTitle()  { return title; }
    public String getCity()   { return city; }
    public Double getTempC()  { return tempC; }
    public Double getUvIndex(){ return uvIndex; }
    public String getAdvice() { return advice; }
    public String getError()  { return error; }
}
