package ru.mirea.golysheva.lesson9.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.golysheva.lesson9.R;
import ru.mirea.golysheva.lesson9.data.repository.MovieRepositoryImpl;
import ru.mirea.golysheva.lesson9.domain.models.Movie;
import ru.mirea.golysheva.lesson9.domain.repository.MovieRepository;
import ru.mirea.golysheva.lesson9.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.golysheva.lesson9.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    private MovieRepository repository;
    private SaveMovieToFavoriteUseCase saveUC;
    private GetFavoriteFilmUseCase getUC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new MovieRepositoryImpl(getApplicationContext());
        saveUC = new SaveMovieToFavoriteUseCase(repository);
        getUC = new GetFavoriteFilmUseCase(repository);

        EditText edit = findViewById(R.id.editTextMovie);
        TextView tv = findViewById(R.id.textViewMovie);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean ok = saveUC.execute(new Movie(2, edit.getText().toString()));
                tv.setText(ok ? "Сохранено" : "Ошибка: пустое имя");
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Movie m = getUC.execute();
                tv.setText(m.getName().isEmpty() ? "Нет данных!" : "Любимый фильм: " + m.getName());
            }
        });
    }
}
