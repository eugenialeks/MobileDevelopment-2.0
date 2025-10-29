package ru.mirea.golysheva.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EventViewModel viewModel;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        viewModel.getEvents().observe(this, eventList -> {
            adapter.setEvents(eventList);
        });

        viewModel.loadHistoricalEvents();
    }
}