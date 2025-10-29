package ru.mirea.golysheva.listviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.text_view_title);
        TextView authorTextView = convertView.findViewById(R.id.text_view_author);

        if (currentBook != null) {
            titleTextView.setText(currentBook.getTitle());
            authorTextView.setText(currentBook.getAuthor());
        }

        return convertView;
    }
}
