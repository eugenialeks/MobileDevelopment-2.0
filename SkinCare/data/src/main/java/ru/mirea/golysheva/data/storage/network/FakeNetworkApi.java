package ru.mirea.golysheva.data.storage.network;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.domain.models.Product;

public class FakeNetworkApi implements NetworkApi {

    private final List<Product> stub = new ArrayList<>();

    public FakeNetworkApi() {
        stub.add(new Product(
                "1",
                "Hydrating Cleanser",
                499,
                "clean",
                "prod_cleanser",
                "Нежный очищающий гель для ежедневного ухода"
        ));
        stub.add(new Product(
                "2",
                "Vitamin C Serum",
                1299,
                "serum",
                "prod_serum",
                "Сыворотка с витамином C для сияния и выравнивания тона кожи"
        ));
        stub.add(new Product(
                "3",
                "SPF 50",
                899,
                "spf",
                "prod_spf",
                "Лёгкий солнцезащитный крем SPF 50 на каждый день"
        ));
    }

    @Override
    public List<Product> fetchProducts() {
        return new ArrayList<>(stub);
    }

    @Override
    public Product fetchById(String id) {
        for (Product p : stub) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
}
