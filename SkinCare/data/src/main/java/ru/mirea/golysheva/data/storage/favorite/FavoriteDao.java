package ru.mirea.golysheva.data.storage.favorite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductFavoriteEntity e);

    @Query("DELETE FROM favorites WHERE productId = :id")
    void delete(String id);

    @Query("SELECT * FROM favorites")
    List<ProductFavoriteEntity> getAll();
}
