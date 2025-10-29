package ru.mirea.golysheva.scrollviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.linear_layout_container);

        LayoutInflater inflater = getLayoutInflater();

        BigInteger currentValue = BigInteger.ONE; // 2^0

        for (int i = 0; i < 100; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_simple_text, container, false);

            String textToShow = (i + 1) + ". " + currentValue.toString();
            textView.setText(textToShow);

            container.addView(textView);

            currentValue = currentValue.multiply(BigInteger.valueOf(2));
        }
    }
}