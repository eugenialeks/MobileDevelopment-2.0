package ru.mirea.golysheva.skincare.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.MaterialToolbar;

import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.details.ProductDetailsViewModel;
import ru.mirea.golysheva.skincare.presentation.details.ProductDetailsVmFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";
    private static final String EXTRA_IMG_URL = "img_url";

    private ProductDetailsViewModel viewModel;
    private String productId;
    private MenuItem favItem;

    public static Intent intent(Context c, String id, String imgUrl) {
        return new Intent(c, ProductDetailsActivity.class)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_IMG_URL, imgUrl);
    }

    @Override protected void onCreate(@Nullable Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_product_details);

        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        productId = getIntent().getStringExtra(EXTRA_ID);
        String imgUrl = getIntent().getStringExtra(EXTRA_IMG_URL);

        viewModel = new ViewModelProvider(this,
                new ProductDetailsVmFactory(this)).get(ProductDetailsViewModel.class);

        ImageView iv = findViewById(R.id.img);
        Glide.with(this)
                .load(imgUrl)
                .override(Target.SIZE_ORIGINAL)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .placeholder(R.drawable.ic_placeholder)
                .into(iv);

        TextView tvName = findViewById(R.id.name);
        TextView tvPrice = findViewById(R.id.price);
        TextView tvDesc = findViewById(R.id.desc);

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
        });

        viewModel.error.observe(this, error -> {
            if (error != null) {
            }
        });

        viewModel.loadProduct(productId);

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