package ru.mirea.golysheva.data.storage.network.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import ru.mirea.golysheva.domain.models.Product;

public class ProductResponse {
    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }
}