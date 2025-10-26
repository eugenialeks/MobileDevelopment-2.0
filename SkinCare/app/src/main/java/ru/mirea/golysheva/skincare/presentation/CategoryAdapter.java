package ru.mirea.golysheva.skincare.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.mirea.golysheva.skincare.R;

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    interface OnClick { void onCategory(HomeFragment.Category c); }

    private final List<HomeFragment.Category> items;
    private final OnClick onClick;

    CategoryAdapter(List<HomeFragment.Category> items, OnClick onClick){
        this.items = items; this.onClick = onClick;
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView icon; TextView title;
        Holder(@NonNull View v){ super(v); icon=v.findViewById(R.id.icon); title=v.findViewById(R.id.title); }
    }

    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_category, p, false));
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int i) {
        HomeFragment.Category c = items.get(i);
        h.icon.setImageResource(c.icon);
        h.title.setText(c.title);
        h.itemView.setOnClickListener(v -> onClick.onCategory(c));
    }

    @Override public int getItemCount() { return items.size(); }
}
