package ru.mirea.golysheva.domain.usecases.products;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class GetProductById {
    private final ProductRepository repo;
    public GetProductById(ProductRepository repo) { this.repo = repo; }
    public Product execute(String id) { return repo.getProductById(id); }
}