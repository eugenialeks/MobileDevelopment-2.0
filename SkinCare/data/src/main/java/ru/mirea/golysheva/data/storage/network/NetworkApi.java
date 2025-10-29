package ru.mirea.golysheva.data.storage.network;

import java.io.IOException;
import java.util.List;
import ru.mirea.golysheva.domain.models.Product;

public interface NetworkApi {
    List<Product> fetchProducts() throws IOException;
    Product fetchById(String id) throws IOException;
}