package ru.mirea.golysheva.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book("1984", "Джордж Оруэлл"));
        bookList.add(new Book("Дюна", "Фрэнк Герберт"));
        bookList.add(new Book("Игра Эндера", "Орсон Скотт Кард"));
        bookList.add(new Book("О дивный новый мир", "Олдос Хаксли"));
        bookList.add(new Book("451 градус по Фаренгейту", "Рэй Брэдбери"));
        bookList.add(new Book("Автостопом по галактике", "Дуглас Адамс"));
        bookList.add(new Book("Нейромант", "Уильям Гибсон"));
        bookList.add(new Book("Гиперион", "Дэн Симмонс"));
        bookList.add(new Book("Солярис", "Станислав Лем"));
        bookList.add(new Book("Властелин колец", "Дж. Р. Р. Толкин"));
        bookList.add(new Book("Основание", "Айзек Азимов"));
        bookList.add(new Book("Мечтают ли андроиды об электроовцах?", "Филип К. Дик"));
        bookList.add(new Book("Схизматрица", "Брюс Стерлинг"));
        bookList.add(new Book("Город", "Клиффорд Саймак"));
        bookList.add(new Book("Задача трёх тел", "Лю Цысинь"));
        bookList.add(new Book("Видоизменённый углерод", "Ричард Морган"));
        bookList.add(new Book("Цветы для Элджернона", "Дэниел Киз"));
        bookList.add(new Book("Бойня номер пять", "Курт Воннегут"));
        bookList.add(new Book("Звёздный десант", "Роберт Хайнлайн"));
        bookList.add(new Book("Заводной апельсин", "Энтони Бёрджесс"));
        bookList.add(new Book("Пикник на обочине", "Аркадий и Борис Стругацкие"));
        bookList.add(new Book("Я, робот", "Айзек Азимов"));
        bookList.add(new Book("Неукротимая планета", "Гарри Гаррисон"));
        bookList.add(new Book("День триффидов", "Джон Уиндем"));
        bookList.add(new Book("Марсианские хроники", "Рэй Брэдбери"));
        bookList.add(new Book("Лавина", "Нил Стивенсон"));
        bookList.add(new Book("Война миров", "Герберт Уэллс"));
        bookList.add(new Book("Бегущий по лезвию (Мечтают ли андроиды...)", "Филип К. Дик"));
        bookList.add(new Book("Машина времени", "Герберт Уэллс"));
        bookList.add(new Book("Франкенштейн, или Современный Прометей", "Мэри Шелли"));
        bookList.add(new Book("Ложная слепота", "Питер Уоттс"));

        BookAdapter adapter = new BookAdapter(this, bookList);
        listView.setAdapter(adapter);
    }
}