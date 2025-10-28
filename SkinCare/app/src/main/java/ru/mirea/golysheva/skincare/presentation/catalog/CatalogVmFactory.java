package ru.mirea.golysheva.skincare.presentation.catalog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.FakeNetworkApi;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class CatalogVmFactory implements ViewModelProvider.Factory {

    private final Context context;

    public CatalogVmFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            ProductRepository productRepo = new ProductRepositoryImpl(
                    AppDatabase.getInstance(context),
                    new FakeNetworkApi()
            );
            return (T) new CatalogViewModel(productRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}