package ru.mirea.golysheva.skincare.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.FakeNetworkApi;
import ru.mirea.golysheva.data.storage.network.NetworkApi;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;
import ru.mirea.golysheva.skincare.R;

public class CatalogFragment extends Fragment {

    private static final String ARG_CAT = "arg_category_id";

    public static CatalogFragment newInstance(@Nullable String categoryId) {
        CatalogFragment f = new CatalogFragment();
        Bundle b = new Bundle();
        b.putString(ARG_CAT, categoryId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i,
                             @Nullable ViewGroup c,
                             @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_catalog, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        v.findViewById(R.id.btnSearch).setOnClickListener(view ->
                startActivity(new Intent(requireContext(), SearchActivity.class)));

        String cat = getArguments() != null ? getArguments().getString(ARG_CAT) : null;

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            NetworkApi api = new FakeNetworkApi();
            ProductRepository repo = new ProductRepositoryImpl(db, api);

            List<Product> list = new GetProductList(repo).execute();

            if (cat != null && !cat.isEmpty()) {
                List<Product> filtered = new ArrayList<>();
                for (Product p : list) {
                    if (cat.equalsIgnoreCase(p.getCategoryId())) filtered.add(p);
                }
                list = filtered;
            }

            List<Product> finalList = list;

            requireActivity().runOnUiThread(() -> {
                ProductAdapter adapter = new ProductAdapter(p -> {
                    int imgRes = ProductImages.of(p.getImageResName());
                    startActivity(ProductDetailsActivity.intent(requireContext(), p.getId(), imgRes));
                });
                rv.setAdapter(adapter);
                adapter.submit(finalList);
            });
        }).start();
    }
}
