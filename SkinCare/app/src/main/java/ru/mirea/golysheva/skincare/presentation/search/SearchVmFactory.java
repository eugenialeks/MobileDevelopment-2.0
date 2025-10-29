package ru.mirea.golysheva.skincare.presentation.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.RetrofitNetworkApi;
import ru.mirea.golysheva.domain.repository.ProductRepository;

public class SearchVmFactory implements ViewModelProvider.Factory {

    private final Context context;

    public SearchVmFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            ProductRepository productRepo = new ProductRepositoryImpl(
                    AppDatabase.getInstance(context),
                    new RetrofitNetworkApi()
            );
            return (T) new SearchViewModel(productRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}