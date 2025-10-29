package ru.mirea.golysheva.domain.models;

public class Product {
    private final String id;
    private final String name;
    private final int price;
    private final String categoryId;
    private final String imageUrl;
    private final String description;

    public Product(String id, String name, int price,
                   String categoryId, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getId()            { return id; }
    public String getName()          { return name; }
    public int getPrice()            { return price; }
    public String getCategoryId()    { return categoryId; }
    public String getImageUrl()      { return imageUrl; }
    public String getDescription()   { return description; }
}