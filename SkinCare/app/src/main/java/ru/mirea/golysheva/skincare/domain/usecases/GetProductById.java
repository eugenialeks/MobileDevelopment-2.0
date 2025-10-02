package ru.mirea.golysheva.skincare.domain.usecases;

import ru.mirea.golysheva.skincare.domain.models.Product;
import ru.mirea.golysheva.skincare.domain.repository.ProductRepository;

public class GetProductById {
    private final ProductRepository repo;
    public GetProductById(ProductRepository repo) { this.repo = repo; }
    public Product execute(String id) { return repo.getProductById(id); }
}