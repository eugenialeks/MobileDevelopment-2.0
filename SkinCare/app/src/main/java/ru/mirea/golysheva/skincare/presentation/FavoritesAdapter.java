package ru.mirea.golysheva.skincare.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.skincare.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.VH> {

    public interface OnItemClick { void onItem(Product p); }
    public interface OnHeartClick { void onHeart(Product p, int pos); }

    private final List<Product> items = new ArrayList<>();
    private final OnItemClick onItem;
    private final OnHeartClick onHeart;

    public FavoritesAdapter(OnItemClick onItem, OnHeartClick onHeart) {
        this.onItem = onItem;
        this.onHeart = onHeart;
    }

    public void submit(List<Product> data) {
        items.clear();
        if (data != null) items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = items.get(pos);

        h.tvName.setText(p.getName());
        h.tvPrice.setText(p.getPrice() + " ₽");

        int resId = h.itemView.getResources().getIdentifier(
                p.getImageResName(), "drawable",
                h.itemView.getContext().getPackageName());
        h.img.setImageResource(resId != 0 ? resId : R.drawable.ic_placeholder);

        boolean fav = FavoritesIconState.cacheContains(h.itemView.getContext(), p.getId());

        h.btnFav.setImageResource(fav ? R.drawable.ic_heart_filled : R.drawable.favorite);
        int tint = androidx.core.content.ContextCompat.getColor(
                h.itemView.getContext(), fav ? R.color.heart_red : R.color.sc_text_main);
        androidx.core.widget.ImageViewCompat.setImageTintList(h.btnFav,
                android.content.res.ColorStateList.valueOf(tint));

        h.itemView.setOnClickListener(v -> onItem.onItem(p));
        h.btnFav.setOnClickListener(v -> {
            onHeart.onHeart(p, h.getBindingAdapterPosition());
            boolean nowFav = !fav;
            h.btnFav.setImageResource(nowFav ? R.drawable.ic_heart_filled : R.drawable.favorite);
            int tintNow = androidx.core.content.ContextCompat.getColor(
                    h.itemView.getContext(), nowFav ? R.color.heart_red : R.color.sc_text_main);
            androidx.core.widget.ImageViewCompat.setImageTintList(
                    h.btnFav, android.content.res.ColorStateList.valueOf(tintNow));
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView img;
        ImageButton btnFav;
        TextView tvName, tvPrice;
        VH(@NonNull View itemView) {
            super(itemView);
            card = (MaterialCardView) itemView;
            img = itemView.findViewById(R.id.img);
            btnFav = itemView.findViewById(R.id.btnFav);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
