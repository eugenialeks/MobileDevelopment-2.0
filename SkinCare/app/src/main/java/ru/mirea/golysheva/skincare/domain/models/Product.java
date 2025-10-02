package ru.mirea.golysheva.skincare.domain.models;

public class Product {
    private final String id;
    private final String title;
    private final String imageUrl;

    public Product(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
}