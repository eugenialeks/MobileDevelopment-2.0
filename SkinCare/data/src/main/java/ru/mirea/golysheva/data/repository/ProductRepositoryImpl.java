package ru.mirea.golysheva.data.repository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.favorite.ProductDao;
import ru.mirea.golysheva.data.storage.models.ProductEntity;
import ru.mirea.golysheva.data.storage.network.NetworkApi;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductDao dao;
    private final NetworkApi api;

    public ProductRepositoryImpl(AppDatabase db, NetworkApi api) {
        this.dao = db.productDao();
        this.api = api;
    }

    private static Product toDomain(ProductEntity e) {
        return new Product(e.id, e.name, e.price, e.categoryId, e.imageResName, e.description);
    }
    private static ProductEntity toEntity(Product p) {
        ProductEntity e = new ProductEntity();
        e.id = p.getId();
        e.name = p.getName();
        e.price = p.getPrice();
        e.categoryId = p.getCategoryId();
        e.imageResName = p.getImageResName();
        e.description = p.getDescription();
        return e;
    }

    @Override
    public List<Product> getProductList() {
        return getProductListInternal(null);
    }

    public List<Product> getProductList(String categoryId) {
        return getProductListInternal(categoryId);
    }

    // Общая реализация
    private List<Product> getProductListInternal(@Nullable String categoryIdOrNull) {
        List<ProductEntity> toSave = new ArrayList<>();
        for (Product p : api.fetchProducts()) toSave.add(toEntity(p));
        dao.upsertAll(toSave);

        List<ProductEntity> fromDb = (categoryIdOrNull == null)
                ? dao.getAll()
                : dao.getByCategory(categoryIdOrNull);

        List<Product> out = new ArrayList<>();
        for (ProductEntity e : fromDb) out.add(toDomain(e));
        return out;
    }

    @Override
    public Product getProductById(String id) {
        ProductEntity e = dao.getById(id);
        if (e != null) return toDomain(e);

        Product fromNet = api.fetchById(id);
        if (fromNet != null) dao.upsert(toEntity(fromNet));
        return fromNet;
    }
}
