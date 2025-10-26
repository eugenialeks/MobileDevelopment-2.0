package ru.mirea.golysheva.data.storage.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {
    @PrimaryKey @NonNull public String id;
    public String name;
    public int    price;
    public String categoryId;
    public String imageResName;
    public String description;
}

