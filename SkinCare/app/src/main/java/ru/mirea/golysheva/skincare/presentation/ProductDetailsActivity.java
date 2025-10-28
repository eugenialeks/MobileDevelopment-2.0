package ru.mirea.golysheva.skincare.presentation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.details.ProductDetailsViewModel;
import ru.mirea.golysheva.skincare.presentation.details.ProductDetailsVmFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";
    private static final String EXTRA_IMG_RES = "img_res";

    private ProductDetailsViewModel viewModel;
    private String productId;
    private MenuItem favItem;

    public static Intent intent(Context c, String id, int imgRes) {
        return new Intent(c, ProductDetailsActivity.class)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_IMG_RES, imgRes);
    }

    @Override protected void onCreate(@Nullable Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_product_details);

        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        productId = getIntent().getStringExtra(EXTRA_ID);
        int imgRes = getIntent().getIntExtra(EXTRA_IMG_RES, R.drawable.ic_placeholder);

        // Инициализация ViewModel
        viewModel = new ViewModelProvider(this,
                new ProductDetailsVmFactory(this)).get(ProductDetailsViewModel.class);

        ImageView iv = findViewById(R.id.img);
        iv.setImageResource(imgRes);

        TextView tvName = findViewById(R.id.name);
        TextView tvPrice = findViewById(R.id.price);
        TextView tvDesc = findViewById(R.id.desc);

        // Наблюдение за данными
        viewModel.product.observe(this, product -> {
            if (product != null) {
                tvName.setText(product.getName());
                tvPrice.setText(product.getPrice() + " ₽");
                tvDesc.setText(product.getDescription());
            }
        });

        viewModel.isFavorite.observe(this, isFavorite -> {
            if (favItem != null) {
                favItem.setIcon(isFavorite ? R.drawable.ic_heart_filled : R.drawable.favorite);
            }
        });

        viewModel.isLoading.observe(this, isLoading -> {
            // Показать/скрыть загрузку
        });

        viewModel.error.observe(this, error -> {
            if (error != null) {
                // Показать ошибку
            }
        });

        // Загрузка данных
        viewModel.loadProduct(productId);

        // Секции
        setupSection(findViewById(R.id.secIngredients),
                getString(R.string.sec_ingredients),
                getString(R.string.demo_ingredients));
        setupSection(findViewById(R.id.secHowTo),
                getString(R.string.sec_howto),
                getString(R.string.demo_howto));
        setupSection(findViewById(R.id.secForWhom),
                getString(R.string.sec_forwhom),
                getString(R.string.demo_forwhom));
    }

    @Override public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        favItem = menu.findItem(R.id.action_fav);

        // Устанавливаем обработчик клика
        favItem.setOnMenuItemClickListener(item -> {
            viewModel.toggleFavorite(productId);
            return true;
        });

        return true;
    }

    private void setupSection(android.view.View root, String title, String content) {
        TextView t = root.findViewById(R.id.title);
        TextView c = root.findViewById(R.id.content);
        ImageView a = root.findViewById(R.id.arrow);
        t.setText(title);
        c.setText(content);
        root.findViewById(R.id.header).setOnClickListener(v -> {
            boolean show = c.getVisibility() != android.view.View.VISIBLE;
            c.setVisibility(show ? android.view.View.VISIBLE : android.view.View.GONE);
            a.animate().rotationBy(show ? 180f : -180f).setDuration(180).start();
        });
    }
}