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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
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

        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            if (adapter == null) {
                adapter = new ProductAdapter(p -> {
                    startActivity(ProductDetailsActivity.intent(requireContext(), p.getId(), p.getImageUrl()));
                });
                rv.setAdapter(adapter);
            }
            adapter.submit(products);
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            Log.d(TAG, "Состояние загрузки: " + isLoading);
        });

        v.findViewById(R.id.btnSearch).setOnClickListener(view -> {
            startActivity(new Intent(requireContext(), SearchActivity.class));
        });

        String categoryId = null;
        if (getArguments() != null) {
            categoryId = getArguments().getString(ARG_CAT);
        }

        Log.d(TAG, "Загрузка продуктов для категории: " + (categoryId != null ? categoryId : "все"));
        viewModel.loadProducts(categoryId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}