package ru.mirea.golysheva.domain.repository;

import java.util.List;
import ru.mirea.golysheva.domain.models.Product;

public interface ProductRepository {
    List<Product> getProductList();
    List<Product> getProductList(String categoryId);
    Product getProductById(String id);
}


