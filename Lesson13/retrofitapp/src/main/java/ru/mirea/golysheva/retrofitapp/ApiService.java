package ru.mirea.golysheva.retrofitapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("todos")
    Call<List<Todo>> getTodos();
}
