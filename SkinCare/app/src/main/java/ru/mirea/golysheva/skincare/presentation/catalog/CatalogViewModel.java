package ru.mirea.golysheva.skincare.presentation.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;

public class CatalogViewModel extends ViewModel {

    private static final String TAG = "CatalogViewModel";
    private final ProductRepository productRepository;

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public final LiveData<List<Product>> products = _products;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    public CatalogViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        Log.d(TAG, "CatalogViewModel создан");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "CatalogViewModel уничтожен");
    }

    public void loadProducts(String categoryId) {
        Log.d(TAG, "Загрузка продуктов, категория: " + (categoryId != null ? categoryId : "все"));
        _isLoading.setValue(true);
        _error.setValue(null);

        new Thread(() -> {
            try {
                Log.d(TAG, "Начало загрузки данных в фоновом потоке");
                List<Product> productList;
                if (categoryId != null && !categoryId.isEmpty()) {
                    productList = productRepository.getProductList(categoryId);
                    Log.d(TAG, "Загружено продуктов категории: " + productList.size());
                } else {
                    productList = new GetProductList(productRepository).execute();
                    Log.d(TAG, "Загружено всех продуктов: " + productList.size());
                }
                _products.postValue(productList);
                Log.d(TAG, "LiveData обновлена с " + productList.size() + " продуктами");
            } catch (Exception e) {
                Log.e(TAG, "Ошибка загрузки продуктов: " + e.getMessage(), e);
                _error.postValue("Ошибка загрузки: " + e.getMessage());
            } finally {
                _isLoading.postValue(false);
                Log.d(TAG, "Загрузка завершена");
            }
        }).start();
    }

    public String getDebugInfo() {
        int productCount = _products.getValue() != null ? _products.getValue().size() : 0;
        String debugInfo = "CatalogViewModel Debug - Продукты: " + productCount +
                ", Загрузка: " + _isLoading.getValue() +
                ", Ошибка: " + (_error.getValue() != null);
        Log.d(TAG, "Отладка: " + debugInfo);
        return debugInfo;
    }
}