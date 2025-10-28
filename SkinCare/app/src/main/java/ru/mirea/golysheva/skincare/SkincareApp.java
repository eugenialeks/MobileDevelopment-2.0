package ru.mirea.golysheva.skincare;

import android.app.Application;
import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.FakeNetworkApi;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class SkincareApp extends Application {
    private ProductRepository productRepo;

    @Override public void onCreate() {
        super.onCreate();
        AppDatabase db = AppDatabase.getInstance(this);
        productRepo = new ProductRepositoryImpl(db, new FakeNetworkApi());
    }

    public ProductRepository productRepository() { return productRepo; }
}
