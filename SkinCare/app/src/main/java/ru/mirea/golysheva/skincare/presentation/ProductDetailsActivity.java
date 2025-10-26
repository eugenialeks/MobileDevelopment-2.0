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

import com.google.android.material.appbar.MaterialToolbar;

import ru.mirea.golysheva.data.repository.FavoritesRepository;
import ru.mirea.golysheva.skincare.R;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";
    private static final String EXTRA_IMG_RES = "img_res";

    public static Intent intent(Context c, String id, int imgRes) {
        return new Intent(c, ProductDetailsActivity.class)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_IMG_RES, imgRes);
    }

    private String productId;
    private FavoritesRepository favorites;
    private MenuItem favItem;

    @Override protected void onCreate(@Nullable Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_product_details);

        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        productId = getIntent().getStringExtra(EXTRA_ID);
        int imgRes = getIntent().getIntExtra(EXTRA_IMG_RES, R.drawable.ic_placeholder);

        favorites = new FavoritesRepository(this);

        ImageView iv = findViewById(R.id.img);
        iv.setImageResource(imgRes); // показ сразу

        TextView tvName  = findViewById(R.id.name);
        TextView tvPrice = findViewById(R.id.price);
        TextView tvDesc  = findViewById(R.id.desc);

        // Загрузка данных (как было)
        new Thread(() -> {
            ru.mirea.golysheva.domain.repository.ProductRepository repo =
                    new ru.mirea.golysheva.data.repository.ProductRepositoryImpl(
                            ru.mirea.golysheva.data.storage.favorite.AppDatabase.getInstance(this),
                            new ru.mirea.golysheva.data.storage.network.FakeNetworkApi()
                    );

            ru.mirea.golysheva.domain.models.Product p =
                    new ru.mirea.golysheva.domain.usecases.products.GetProductById(repo)
                            .execute(productId);

            runOnUiThread(() -> {
                if (p != null) {
                    tvName.setText(p.getName());
                    tvPrice.setText(p.getPrice() + " ₽");
                    tvDesc.setText(p.getDescription());
                    // если хочется заменить placeholder на «точную» картинку из имени:
                    // int real = getResources().getIdentifier(p.getImageResName(),"drawable",getPackageName());
                    // if (real != 0) iv.setImageResource(real);
                }
            });
        }).start();

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
        updateFavIcon();
        favItem.setOnMenuItemClickListener(i -> { toggleFavorite(); return true; });
        return true;
    }

    private void updateFavIcon() {
        boolean isFav = favorites.contains(productId);
        favItem.setIcon(isFav ? R.drawable.ic_heart_filled : R.drawable.favorite);
    }

    private void toggleFavorite() {
        boolean nowFav = favorites.toggle(productId);
        favItem.setIcon(nowFav ? R.drawable.ic_heart_filled : R.drawable.favorite);
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
