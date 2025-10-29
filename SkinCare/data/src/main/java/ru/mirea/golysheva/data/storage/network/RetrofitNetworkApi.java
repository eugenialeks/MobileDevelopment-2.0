package ru.mirea.golysheva.data.storage.network;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import ru.mirea.golysheva.data.storage.network.dto.ProductResponse;
import ru.mirea.golysheva.domain.models.Product;

public class RetrofitNetworkApi implements NetworkApi {

    private final RetrofitClient.ApiService apiService;

    public RetrofitNetworkApi() {
        this.apiService = RetrofitClient.getApiService();
    }

    @Override
    public List<Product> fetchProducts() throws IOException {
        Response<ProductResponse> response = apiService.fetchProducts().execute();

        if (response.isSuccessful() && response.body() != null && response.body().getProducts() != null) {
            return response.body().getProducts();
        } else {
            throw new IOException("API call failed with response code: " + response.code());
        }
    }

    @Override
    public Product fetchById(String id) throws IOException {
        List<Product> allProducts = fetchProducts();
        for (Product p : allProducts) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}