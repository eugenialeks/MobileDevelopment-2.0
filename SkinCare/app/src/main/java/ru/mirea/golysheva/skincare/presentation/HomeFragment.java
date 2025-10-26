package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;
import ru.mirea.golysheva.skincare.R;

public class HomeFragment extends Fragment {

    static class Category {
        final String id, title; final int icon;
        Category(String id, String title, int icon){this.id=id; this.title=title; this.icon=icon;}
    }

    private final List<Category> data = Arrays.asList(
            new Category("clean", "Очищающие\nсредства", R.drawable.ic_drop),
            new Category("moist", "Увлажняющие\nсредства", R.drawable.ic_leaf),
            new Category("spf",   "Солнцезащитные\nсредства", R.drawable.sun),
            new Category("serum", "Сыворотки", R.drawable.ic_flask)
    );

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf, @Nullable ViewGroup c, @Nullable Bundle b) {
        return inf.inflate(R.layout.fragment_home, c, false);
    }

    @Override public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        RecyclerView rv = v.findViewById(R.id.rvCategories);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(new CategoryAdapter(data, cat -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, CatalogFragment.newInstance(cat.id))
                    .addToBackStack(null)
                    .commit();
        }));

        v.findViewById(R.id.btnSearch).setOnClickListener(view ->
                startActivity(new android.content.Intent(requireContext(), SearchActivity.class)));

        ((TextView) v.findViewById(R.id.tvTip))
                .setText("Не забывайте повторно наносить солнцезащитный крем каждые два часа для оптимальной защиты.");
    }
}
