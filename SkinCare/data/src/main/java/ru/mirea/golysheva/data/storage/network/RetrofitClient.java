package ru.mirea.golysheva.data.storage.network;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mirea.golysheva.data.storage.network.dto.ProductResponse;
import ru.mirea.golysheva.domain.models.Product;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.mocki.io/v2/ia032k9y/";

    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    public interface ApiService {
        @GET(".")
        Call<ProductResponse> fetchProducts();

        @GET("{id}")
        Call<Product> fetchById(@Path("id") String id);
    }
}