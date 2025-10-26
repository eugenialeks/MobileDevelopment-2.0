package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.data.repository.FavoritesRepository;
import ru.mirea.golysheva.data.repository.ProductRepositoryImpl;
import ru.mirea.golysheva.data.storage.favorite.AppDatabase;
import ru.mirea.golysheva.data.storage.network.FakeNetworkApi;
import ru.mirea.golysheva.data.storage.network.NetworkApi;
import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;
import ru.mirea.golysheva.skincare.R;

public class FavoritesFragment extends Fragment {

    public FavoritesFragment() { super(R.layout.fragment_favorites); }

    private FavoritesRepository favorites;
    private FavoritesAdapter adapter;
    private final List<Product> allProducts = new ArrayList<>();

    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        MaterialToolbar tb = v.findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(
                click -> requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );

        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setHasFixedSize(true);

        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing_16);
        rv.addItemDecoration(new GridSpacingDecoration(2, spacing, true));

        adapter = new FavoritesAdapter(
                p -> startActivity(ProductDetailsActivity.intent(requireContext(), p.getId(),
                        v.getResources().getIdentifier(
                                p.getImageResName(), "drawable", requireContext().getPackageName()))),
                (p, pos) -> {
                    favorites.toggle(p.getId());
                    bindFavorites();
                }
        );
        rv.setAdapter(adapter);

        favorites = new FavoritesRepository(requireContext());

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            NetworkApi api = new FakeNetworkApi();
            ProductRepository repo = new ProductRepositoryImpl(db, api);
            List<Product> loaded = new GetProductList(repo).execute();

            requireActivity().runOnUiThread(() -> {
                allProducts.clear();
                allProducts.addAll(loaded);
                bindFavorites();
            });
        }).start();
    }

    private void bindFavorites() {
        favorites.getAll(ids -> {
            List<Product> onlyFav = new ArrayList<>();
            for (Product p : allProducts) if (ids.contains(p.getId())) onlyFav.add(p);
            requireActivity().runOnUiThread(() -> {
                adapter.submit(onlyFav);
            });
        });
    }
}
