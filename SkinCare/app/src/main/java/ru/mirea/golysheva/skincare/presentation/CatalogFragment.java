package ru.mirea.golysheva.skincare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.catalog.CatalogViewModel;
import ru.mirea.golysheva.skincare.presentation.catalog.CatalogVmFactory;

public class CatalogFragment extends Fragment {

    private static final String TAG = "CatalogFragment";
    private static final String ARG_CAT = "arg_category_id";
    private CatalogViewModel viewModel;
    private ProductAdapter adapter;

    public static CatalogFragment newInstance(@Nullable String categoryId) {
        CatalogFragment f = new CatalogFragment();
        Bundle b = new Bundle();
        b.putString(ARG_CAT, categoryId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        Log.d(TAG, "onCreateView вызван");
        return i.inflate(R.layout.fragment_catalog, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        Log.d(TAG, "onViewCreated вызван");

        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Log.d(TAG, "Создание CatalogViewModel");
        viewModel = new ViewModelProvider(this, new CatalogVmFactory(requireContext())).get(CatalogViewModel.class);

        Log.d(TAG, "Тестирование ViewModel: " + viewModel.getDebugInfo());

        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            Log.d(TAG, "LiveData обновлена! Количество продуктов: " + products.size());

            if (adapter == null) {
                Log.d(TAG, "Создание ProductAdapter");
                adapter = new ProductAdapter(p -> {
                    Log.d(TAG, "Продукт нажат: " + p.getName());
                    // Изменено: передаем URL изображения
                    startActivity(ProductDetailsActivity.intent(requireContext(), p.getId(), p.getImageUrl()));
                });
                rv.setAdapter(adapter);
            }
            adapter.submit(products);
            Log.d(TAG, "UI обновлен с " + products.size() + " продуктами");
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            Log.d(TAG, "Состояние загрузки: " + isLoading);
        });

        viewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e(TAG, "Наблюдаемая ошибка: " + error);
            }
        });

        v.findViewById(R.id.btnSearch).setOnClickListener(view -> {
            Log.d(TAG, "Кнопка поиска нажата");
            startActivity(new Intent(requireContext(), SearchActivity.class));
        });

        String categoryId = getArguments() != null ? getArguments().getString(ARG_CAT) : null;
        Log.d(TAG, "Загрузка продуктов для категории: " + (categoryId != null ? categoryId : "все"));
        viewModel.loadProducts(categoryId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView вызван");
    }
}