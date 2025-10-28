package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.golysheva.domain.models.Product;
import ru.mirea.golysheva.skincare.R;
import ru.mirea.golysheva.skincare.presentation.search.SearchViewModel;
import ru.mirea.golysheva.skincare.presentation.search.SearchVmFactory;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel viewModel;
    private ProductAdapter adapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText et = findViewById(R.id.etQuery);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel = new ViewModelProvider(this,
                new SearchVmFactory(this)).get(SearchViewModel.class);

        adapter = new ProductAdapter(p -> {
            int imgRes = ProductImages.of(p.getImageResName());
            startActivity(ProductDetailsActivity.intent(this, p.getId(), imgRes));
        });
        rv.setAdapter(adapter);

        viewModel.searchResults.observe(this, products -> {
            adapter.submit(products);
        });

        viewModel.isLoading.observe(this, isLoading -> {
        });

        viewModel.loadProducts();

        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                viewModel.setSearchQuery(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}