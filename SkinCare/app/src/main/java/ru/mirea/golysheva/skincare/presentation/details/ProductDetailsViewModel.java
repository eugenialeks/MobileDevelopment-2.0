package ru.mirea.golysheva.skincare.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import ru.mirea.golysheva.data.repository.FavoritesRepository;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductById;

public class ProductDetailsViewModel extends ViewModel {

    private static final String TAG = "ProductDetailsViewModel";
    private final ProductRepository productRepository;
    private final FavoritesRepository favoritesRepository;

    private final MutableLiveData<Product> _product = new MutableLiveData<>();
    public final LiveData<Product> product = _product;

    private final MutableLiveData<Boolean> _isFavorite = new MutableLiveData<>(false);
    public final LiveData<Boolean> isFavorite = _isFavorite;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    public ProductDetailsViewModel(ProductRepository productRepository, FavoritesRepository favoritesRepository) {
        this.productRepository = productRepository;
        this.favoritesRepository = favoritesRepository;
        Log.d(TAG, "ProductDetailsViewModel создан");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ProductDetailsViewModel уничтожен");
    }

    public void loadProduct(String productId) {
        Log.d(TAG, "Загрузка продукта по ID: " + productId);
        _isLoading.setValue(true);
        _error.setValue(null);

        new Thread(() -> {
            try {
                Log.d(TAG, "Загрузка деталей продукта из репозитория");
                Product product = new GetProductById(productRepository).execute(productId);

                if (product != null) {
                    Log.d(TAG, "Продукт загружен: " + product.getName());
                    _product.postValue(product);

                    boolean favorite = favoritesRepository.contains(productId);
                    Log.d(TAG, "Статус избранного: " + favorite);
                    _isFavorite.postValue(favorite);
                } else {
                    Log.w(TAG, "Продукт не найден: " + productId);
                    _error.postValue("Продукт не найден");
                }
            } catch (Exception e) {
                Log.e(TAG, "Ошибка загрузки продукта: " + e.getMessage(), e);
                _error.postValue("Ошибка загрузки продукта: " + e.getMessage());
            } finally {
                _isLoading.postValue(false);
                Log.d(TAG, "Загрузка продукта завершена");
            }
        }).start();
    }

    public void toggleFavorite(String productId) {
        Log.d(TAG, "Переключение избранного для продукта: " + productId);
        boolean newFavoriteState = favoritesRepository.toggle(productId);
        Log.d(TAG, "Избранное переключено на: " + newFavoriteState);
        _isFavorite.setValue(newFavoriteState);
    }

    public String getDebugInfo() {
        Product currentProduct = _product.getValue();
        String debugInfo = "ProductDetailsViewModel Debug - Продукт: " +
                (currentProduct != null ? currentProduct.getName() : "null") +
                ", Избранное: " + _isFavorite.getValue() +
                ", Загрузка: " + _isLoading.getValue() +
                ", Ошибка: " + (_error.getValue() != null);
        Log.d(TAG, "Отладка: " + debugInfo);
        return debugInfo;
    }
}