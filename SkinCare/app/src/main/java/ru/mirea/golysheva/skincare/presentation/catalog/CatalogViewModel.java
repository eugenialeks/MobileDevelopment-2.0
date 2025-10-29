package ru.mirea.golysheva.skincare.presentation.catalog;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        Log.d(TAG, "CatalogViewModel created");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "CatalogViewModel destroyed");
    }

    public void loadProducts(String categoryId) {
        Log.d(TAG, "Loading products, category: " + (categoryId != null ? categoryId : "all"));
        _isLoading.setValue(true);
        _error.setValue(null);

        new Thread(() -> {
            try {
                Log.d(TAG, "Starting data loading in background thread");
                List<Product> productList; // Объявляем переменную здесь

                if (categoryId != null && !categoryId.isEmpty()) {
                    productList = productRepository.getProductList(categoryId);
                    Log.d(TAG, "Loaded category products: " + productList.size());
                } else {
                    productList = new GetProductList(productRepository).execute();
                    Log.d(TAG, "Loaded all products: " + productList.size());
                }

                _products.postValue(productList);
                Log.d(TAG, "LiveData updated with " + productList.size() + " products");
            } catch (Exception e) {
                Log.e(TAG, "Error loading products: " + e.getMessage(), e);
                _error.postValue("Loading error: " + e.getMessage());
            } finally {
                _isLoading.postValue(false);
                Log.d(TAG, "Loading completed");
            }
        }).start();
    }

    public String getDebugInfo() {
        int productCount = _products.getValue() != null ? _products.getValue().size() : 0;
        String debugInfo = "CatalogViewModel Debug - Products: " + productCount +
                ", Loading: " + _isLoading.getValue() +
                ", Error: " + (_error.getValue() != null);
        Log.d(TAG, "Debug: " + debugInfo);
        return debugInfo;
    }
}