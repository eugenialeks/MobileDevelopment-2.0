package ru.mirea.golysheva.skincare.domain.usecases;

import java.util.List;
import ru.mirea.golysheva.skincare.domain.models.Product;
import ru.mirea.golysheva.skincare.domain.repository.ProductRepository;

public class GetProductList {
    private final ProductRepository repo;
    public GetProductList(ProductRepository repo) { this.repo = repo; }
    public List<Product> execute() { return repo.getProductList(); }
}
