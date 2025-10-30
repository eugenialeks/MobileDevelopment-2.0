package ru.mirea.golysheva.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private MovieViewModel viewModel;
    private List<Movie> movies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movies = new ArrayList<>();
        movies.add(new Movie("Интерстеллар", "Кристофер Нолан", 2014));
        movies.add(new Movie("Начало", "Кристофер Нолан", 2010));
        movies.add(new Movie("Криминальное чтиво", "Квентин Тарантино", 1994));
        movies.add(new Movie("Форрест Гамп", "Роберт Земекис", 1994));
        movies.add(new Movie("Властелин колец: Возвращение короля", "Питер Джексон", 2003));
        movies.add(new Movie("Зеленая миля", "Фрэнк Дарабонт", 1999));

        ListView listView = view.findViewById(R.id.list_view_movies);

        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                movies
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            Movie selectedMovie = movies.get(position);

            viewModel.selectMovie(selectedMovie);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, DetailsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}