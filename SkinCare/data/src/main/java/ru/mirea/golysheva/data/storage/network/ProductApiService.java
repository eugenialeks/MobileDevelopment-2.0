package ru.mirea.golysheva.data.storage.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mirea.golysheva.domain.models.Product;

public interface ProductApiService {
    @GET("v2/ia032k9y")
    Call<List<Product>> getProducts();
}