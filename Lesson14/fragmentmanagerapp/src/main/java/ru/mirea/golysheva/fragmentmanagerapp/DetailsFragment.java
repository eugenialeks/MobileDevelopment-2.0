package ru.mirea.golysheva.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class DetailsFragment extends Fragment {

    private MovieViewModel viewModel;
    private TextView titleTextView;
    private TextView directorTextView;
    private TextView yearTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = view.findViewById(R.id.text_movie_title);
        directorTextView = view.findViewById(R.id.text_director);
        yearTextView = view.findViewById(R.id.text_year);

        viewModel.getSelectedMovie().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                directorTextView.setText("Режиссер: " + movie.getDirector());
                yearTextView.setText("Год выпуска: " + movie.getYear());
            }
        });
    }
}