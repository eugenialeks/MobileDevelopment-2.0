package ru.mirea.golysheva.domain.models;

public class Product {
    private final String id;
    private final String name;
    private final int price;
    private final String categoryId;
    private final String imageResName;
    private final String description;

    public Product(String id, String name, int price,
                   String categoryId, String imageResName, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.imageResName = imageResName;
        this.description = description;
    }

    public Product(String id, String name, int price, String categoryId) {
        this(id, name, price, categoryId, null, null);
    }

    public String getId()            { return id; }
    public String getName()          { return name; }
    public int getPrice()            { return price; }
    public String getCategoryId()    { return categoryId; }
    public String getImageResName()  { return imageResName; }
    public String getDescription()   { return description; }
}
