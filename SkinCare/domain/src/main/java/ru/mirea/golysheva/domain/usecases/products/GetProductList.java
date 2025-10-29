package ru.mirea.golysheva.domain.usecases.products;

import java.util.List;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class GetProductList {
    private final ProductRepository repo;

    public GetProductList(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> execute() {
        return repo.getProductList();
    }
}