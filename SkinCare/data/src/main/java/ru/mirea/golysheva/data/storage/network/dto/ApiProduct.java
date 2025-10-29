package ru.mirea.golysheva.data.storage.network.dto;

import com.google.gson.annotations.SerializedName;

public class ApiProduct {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("category")
    private String category;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("ingredients")
    private String ingredients;

    @SerializedName("skinType")
    private String skinType;

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public String getIngredients() { return ingredients; }
    public String getSkinType() { return skinType; }
}
