package ru.mirea.golysheva.skincare.presentation.details;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.FavoritesRepository;
import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.FakeNetworkApi;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class ProductDetailsVmFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ProductDetailsVmFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel.class)) {
            ProductRepository productRepo = new ProductRepositoryImpl(
                    AppDatabase.getInstance(context),
                    new FakeNetworkApi()
            );
            FavoritesRepository favoritesRepo = new FavoritesRepository(context);
            return (T) new ProductDetailsViewModel(productRepo, favoritesRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}