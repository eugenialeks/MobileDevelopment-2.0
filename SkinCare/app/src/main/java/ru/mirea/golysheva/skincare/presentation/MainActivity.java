package ru.mirea.golysheva.skincare.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.golysheva.skincare.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottomBar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_scan) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && user.isAnonymous()) {
                    Toast.makeText(this, "Сканирование доступно только авторизованным пользователям", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            navView.getMenu().findItem(destination.getId()).setChecked(true);
        });
    }
}