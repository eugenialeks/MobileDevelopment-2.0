package ru.mirea.golysheva.data.storage.favorite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.mirea.golysheva.data.storage.models.ProductEntity;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertAll(List<ProductEntity> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(ProductEntity entity);

    @Query("SELECT * FROM products")
    List<ProductEntity> getAll();

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    List<ProductEntity> getByCategory(String categoryId);

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    ProductEntity getById(String id);
}
