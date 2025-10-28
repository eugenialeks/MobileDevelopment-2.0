package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.google.android.material.appbar.MaterialToolbar;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.favorites.FavoritesViewModel;
import ru.mirea.golysheva.skincare.presentation.favorites.FavoritesVmFactory;

public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragment";
    private FavoritesViewModel viewModel;
    private FavoritesAdapter adapter;

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
        Log.d(TAG, "FavoritesFragment создан");
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        Log.d(TAG, "onViewCreated вызван");

        MaterialToolbar tb = v.findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(click -> {
            Log.d(TAG, "Кнопка назад нажата");
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Log.d(TAG, "Создание FavoritesViewModel");
        viewModel = new ViewModelProvider(this,
                new FavoritesVmFactory(requireContext())).get(FavoritesViewModel.class);

        Log.d(TAG, "Тестирование ViewModel: " + viewModel.getDebugInfo());

        adapter = new FavoritesAdapter(
                p -> {
                    Log.d(TAG, "Избранный продукт нажат: " + p.getName());
                    startActivity(ProductDetailsActivity.intent(requireContext(), p.getId(),
                            v.getResources().getIdentifier(
                                    p.getImageResName(), "drawable", requireContext().getPackageName())));
                },
                (p, pos) -> {
                    Log.d(TAG, "Переключение избранного для: " + p.getName());
                    viewModel.toggleFavorite(p.getId());
                }
        );
        rv.setAdapter(adapter);

        viewModel.favoriteProducts.observe(getViewLifecycleOwner(), favorites -> {
            Log.d(TAG, "MediatorLiveData скомбинировал избранные: " + favorites.size());
            adapter.submit(favorites);
            Log.d(TAG, "UI обновлен с " + favorites.size() + " избранными продуктами");
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            Log.d(TAG, "Состояние загрузки: " + isLoading);
        });

        Log.d(TAG, "Загрузка данных избранных");
        viewModel.loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView вызван");
    }
}