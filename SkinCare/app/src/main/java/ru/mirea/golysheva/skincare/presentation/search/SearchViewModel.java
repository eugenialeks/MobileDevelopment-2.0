package ru.mirea.golysheva.skincare.presentation.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private final ProductRepository productRepository;

    private final MutableLiveData<List<Product>> _allProducts = new MutableLiveData<>();
    private final MutableLiveData<String> _searchQuery = new MutableLiveData<>("");

    private final MediatorLiveData<List<Product>> _searchResults = new MediatorLiveData<>();
    public final LiveData<List<Product>> searchResults = _searchResults;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    public SearchViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        Log.d(TAG, "SearchViewModel создан");

        _searchResults.addSource(_allProducts, products -> {
            Log.d(TAG, "MediatorLiveData: _allProducts изменился, количество: " +
                    (products != null ? products.size() : 0));
            applyFilter();
        });

        _searchResults.addSource(_searchQuery, query -> {
            Log.d(TAG, "MediatorLiveData: _searchQuery изменен на: '" + query + "'");
            applyFilter();
        });

        Log.d(TAG, "Источники MediatorLiveData настроены");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "SearchViewModel уничтожен");
    }

    public void loadProducts() {
        Log.d(TAG, "Загрузка продуктов вызвана");
        _isLoading.setValue(true);

        new Thread(() -> {
            try {
                Log.d(TAG, "Загрузка продуктов для поиска");
                List<Product> products = new GetProductList(productRepository).execute();
                Log.d(TAG, "Загружено продуктов для поиска: " + products.size());
                _allProducts.postValue(products);
            } catch (Exception e) {
                Log.e(TAG, "Ошибка загрузки продуктов для поиска: " + e.getMessage(), e);
                _allProducts.postValue(new ArrayList<>());
            } finally {
                _isLoading.postValue(false);
                Log.d(TAG, "Загрузка продуктов для поиска завершена");
            }
        }).start();
    }

    public void setSearchQuery(String query) {
        String cleanQuery = query != null ? query.trim().toLowerCase() : "";
        Log.d(TAG, "Установка поискового запроса: '" + cleanQuery + "'");
        _searchQuery.setValue(cleanQuery);
    }

    private void applyFilter() {
        List<Product> allProducts = _allProducts.getValue();
        String query = _searchQuery.getValue();

        Log.d(TAG, "applyFilter вызван - Все продукты: " +
                (allProducts != null ? allProducts.size() : "null") +
                ", Запрос: '" + query + "'");

        if (allProducts == null) {
            Log.d(TAG, "Продукты еще не доступны");
            _searchResults.setValue(new ArrayList<>());
            return;
        }

        if (query == null || query.isEmpty()) {
            Log.d(TAG, "Показ всех продуктов (нет запроса)");
            _searchResults.setValue(allProducts);
            return;
        }

        List<Product> result = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query)) {
                result.add(product);
            }
        }

        Log.d(TAG, "Поиск отфильтрован до " + result.size() + " результатов для запроса: '" + query + "'");
        _searchResults.setValue(result);
    }

    public String getDebugInfo() {
        int allCount = _allProducts.getValue() != null ? _allProducts.getValue().size() : 0;
        int resultCount = _searchResults.getValue() != null ? _searchResults.getValue().size() : 0;
        String query = _searchQuery.getValue();

        String debugInfo = "SearchViewModel Debug - Все: " + allCount +
                ", Результаты: " + resultCount +
                ", Запрос: '" + query + "'" +
                ", Загрузка: " + _isLoading.getValue();
        Log.d(TAG, "Отладка: " + debugInfo);
        return debugInfo;
    }
}