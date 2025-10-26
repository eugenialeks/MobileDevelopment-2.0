package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.domain.repository.ProductRepository;
import ru.mirea.golysheva.domain.usecases.products.GetProductList;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.SkincareApp;

public class SearchActivity extends AppCompatActivity {

    private final List<Product> all = new ArrayList<>();
    private ProductAdapter adapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText et = findViewById(R.id.etQuery);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductAdapter(p -> {
            int imgRes = ProductImages.of(p.getImageResName());
            startActivity(ProductDetailsActivity.intent(this, p.getId(), imgRes));
        });
        rv.setAdapter(adapter);

        new Thread(() -> {
            ProductRepository repo = ((SkincareApp) getApplication()).productRepository();
            List<Product> loaded = new GetProductList(repo).execute();
            runOnUiThread(() -> {
                all.clear();
                all.addAll(loaded);
                applyFilter(et.getText() == null ? "" : et.getText().toString());
            });
        }).start();

        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) { applyFilter(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void applyFilter(String q) {
        String query = q == null ? "" : q.trim().toLowerCase();
        List<Product> result = new ArrayList<>();
        if (query.isEmpty()) {
            result.addAll(all);
        } else {
            for (Product p : all) {
                if (p.getName().toLowerCase().contains(query)) {
                    result.add(p);
                }
            }
        }
        adapter.submit(result);
    }
}
