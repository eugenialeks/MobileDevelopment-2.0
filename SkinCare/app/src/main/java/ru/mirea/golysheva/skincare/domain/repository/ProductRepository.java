package ru.mirea.golysheva.skincare.domain.repository;

import java.util.List;
import ru.mirea.golysheva.skincare.domain.models.Product;

public interface ProductRepository {
    java.util.List<Product> getProductList();
    Product getProductById(String id);
}
