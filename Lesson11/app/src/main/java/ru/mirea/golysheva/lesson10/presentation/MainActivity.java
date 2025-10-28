package ru.mirea.golysheva.lesson10.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.golysheva.lesson10.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private TextView textViewMovie;
    private EditText editTextMovie;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity создана");

        textViewMovie = findViewById(R.id.textViewMovie);
        editTextMovie = findViewById(R.id.editTextMovie);

        mainViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MainViewModel.class);

        mainViewModel.getMovieTextLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String newText) {
                textViewMovie.setText(newText);
                Log.d(TAG, "LiveData обновила текст: " + newText);
            }
        });

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = editTextMovie.getText().toString();
                Log.d(TAG, "Нажата кнопка Сохранить, фильм: " + movieName);
                mainViewModel.saveMovie(movieName);
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Нажата кнопка Отобразить");
                mainViewModel.getMovie();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity уничтожена");
    }
}