package ru.mirea.golysheva.skincare.data.repository;

import java.util.ArrayList;
import java.util.List;
import ru.mirea.golysheva.skincare.domain.models.Product;
import ru.mirea.golysheva.skincare.domain.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> demo = new ArrayList<>();

    public ProductRepositoryImpl() {
        demo.add(new Product("p1","SPF 50 Sunscreen","https://example.com/spf.jpg"));
        demo.add(new Product("p2","Hyaluronic Serum","https://example.com/serum.jpg"));
        demo.add(new Product("p3","Gentle Cleanser","https://example.com/cleanser.jpg"));
    }

    @Override public List<Product> getProductList() { return demo; }

    @Override public Product getProductById(String id) {
        for (Product p : demo) if (p.getId().equals(id)) return p;
        return null;
    }
}