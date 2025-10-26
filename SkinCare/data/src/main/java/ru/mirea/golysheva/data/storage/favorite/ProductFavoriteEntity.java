package ru.mirea.golysheva.data.storage.favorite;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class ProductFavoriteEntity {
    @PrimaryKey @NonNull
    public String productId;

    public ProductFavoriteEntity(@NonNull String productId) {
        this.productId = productId;
    }
}
