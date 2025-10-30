package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.golysheva.skincare.R;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        BottomNavigationView bar = findViewById(R.id.bottomBar);
        bar.setOnItemSelectedListener(item -> {
            Fragment f = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                f = new HomeFragment();
            } else if (id == R.id.nav_catalog) {
                f = new CatalogFragment();
            } else if (id == R.id.nav_scan) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && user.isAnonymous()) {
                    Toast.makeText(this, "Сканирование доступно только авторизованным пользователям", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    f = new ScanFragment();
                }
            } else if (id == R.id.nav_fav) {
                f = new FavoritesFragment();
            } else if (id == R.id.nav_profile) {
                f = new ProfileFragment();
            }

            if (f != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, f).commit();
            }
            return true;
        });

        if (b == null) {
            bar.setSelectedItemId(R.id.nav_home);
        }
    }
}