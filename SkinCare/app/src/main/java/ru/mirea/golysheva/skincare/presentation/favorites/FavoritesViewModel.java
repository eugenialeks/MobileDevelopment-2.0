package ru.mirea.golysheva.skincare.presentation.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.mirea.golysheva.data.repository.FavoritesRepository;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;

public class FavoritesViewModel extends ViewModel {

    private static final String TAG = "FavoritesViewModel";
    private final ProductRepository productRepository;
    private final FavoritesRepository favoritesRepository;

    private final MutableLiveData<List<Product>> _allProducts = new MutableLiveData<>();
    private final MutableLiveData<Set<String>> _favoriteIds = new MutableLiveData<>();

    private final MediatorLiveData<List<Product>> _favoriteProducts = new MediatorLiveData<>();
    public final LiveData<List<Product>> favoriteProducts = _favoriteProducts;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    public FavoritesViewModel(ProductRepository productRepository, FavoritesRepository favoritesRepository) {
        this.productRepository = productRepository;
        this.favoritesRepository = favoritesRepository;
        Log.d(TAG, "FavoritesViewModel создан");

        _favoriteProducts.addSource(_allProducts, products -> {
            Log.d(TAG, "MediatorLiveData: _allProducts изменился, количество: " +
                    (products != null ? products.size() : 0));
            combineFavorites();
        });

        _favoriteProducts.addSource(_favoriteIds, ids -> {
            Log.d(TAG, "MediatorLiveData: _favoriteIds изменился, количество: " +
                    (ids != null ? ids.size() : 0));
            combineFavorites();
        });

        Log.d(TAG, "Источники MediatorLiveData настроены");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "FavoritesViewModel уничтожен");
    }

    public void loadData() {
        Log.d(TAG, "Загрузка данных вызвана");
        _isLoading.setValue(true);

        new Thread(() -> {
            try {
                Log.d(TAG, "Загрузка всех продуктов из репозитория");
                List<Product> products = new GetProductList(productRepository).execute();
                Log.d(TAG, "Загружено продуктов всего: " + products.size());
                _allProducts.postValue(products);
            } catch (Exception e) {
                Log.e(TAG, "Ошибка загрузки продуктов: " + e.getMessage(), e);
                _allProducts.postValue(new ArrayList<>());
            }
        }).start();

        Log.d(TAG, "Загрузка ID избранных из SharedPreferences");
        favoritesRepository.getAll(ids -> {
            Log.d(TAG, "Загружено ID избранных: " + ids.size());
            _favoriteIds.postValue(new HashSet<>(ids));
            _isLoading.postValue(false);
        });
    }

    public void toggleFavorite(String productId) {
        Log.d(TAG, "Переключение избранного для продукта: " + productId);
        boolean newFavoriteState = favoritesRepository.toggle(productId);
        Log.d(TAG, "Статус избранного изменен на: " + newFavoriteState);

        favoritesRepository.getAll(ids -> {
            Log.d(TAG, "Обновление ID избранных, новое количество: " + ids.size());
            _favoriteIds.postValue(new HashSet<>(ids));
        });
    }

    private void combineFavorites() {
        List<Product> allProducts = _allProducts.getValue();
        Set<String> favoriteIds = _favoriteIds.getValue();

        Log.d(TAG, "combineFavorites вызван - Все продукты: " +
                (allProducts != null ? allProducts.size() : "null") +
                ", ID избранных: " + (favoriteIds != null ? favoriteIds.size() : "null"));

        if (allProducts == null || favoriteIds == null) {
            Log.d(TAG, "Ожидание обоих источников данных...");
            return;
        }

        List<Product> favorites = new ArrayList<>();
        for (Product product : allProducts) {
            if (favoriteIds.contains(product.getId())) {
                favorites.add(product);
            }
        }

        Log.d(TAG, "MediatorLiveData скомбинировал " + favorites.size() + " избранных продуктов");
        _favoriteProducts.setValue(favorites);
    }

    public String getDebugInfo() {
        int allCount = _allProducts.getValue() != null ? _allProducts.getValue().size() : 0;
        int favCount = _favoriteIds.getValue() != null ? _favoriteIds.getValue().size() : 0;
        int resultCount = _favoriteProducts.getValue() != null ? _favoriteProducts.getValue().size() : 0;

        String debugInfo = "FavoritesViewModel Debug - Все: " + allCount +
                ", ID избранных: " + favCount +
                ", Скомбинировано: " + resultCount +
                ", Загрузка: " + _isLoading.getValue();
        Log.d(TAG, "Отладка: " + debugInfo);
        return debugInfo;
    }
}