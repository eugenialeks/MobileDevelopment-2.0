package ru.mirea.golysheva.data.storage.network;

import java.util.List;
import ru.mirea.golysheva.domain.models.Product;

public interface NetworkApi {
    List<Product> fetchProducts();
    Product fetchById(String id);
}
