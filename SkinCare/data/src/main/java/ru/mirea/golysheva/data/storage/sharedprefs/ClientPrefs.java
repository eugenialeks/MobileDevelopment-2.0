package ru.mirea.golysheva.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import ru.mirea.golysheva.domain.models.User;

public class ClientPrefs {

    private static final String PREFS = "client_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_NAME  = "name"; // необязательное поле

    private final SharedPreferences sp;

    public ClientPrefs(Context ctx) {
        this.sp = ctx.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveUser(User user) {
        sp.edit()
                .putString(KEY_EMAIL, user.getEmail())
                .putString(KEY_TOKEN, user.getToken())
                .putString(KEY_NAME,  user.getName()) // если в User нет name — убери эту строку
                .apply();
    }

    @Nullable
    public User getUser() {
        String email = sp.getString(KEY_EMAIL, null);
        String token = sp.getString(KEY_TOKEN, null);
        if (email == null || token == null) return null;
        String name  = sp.getString(KEY_NAME, null);
        // Конструктор под 3 аргумента. Если у тебя 2 — верни new User(email, token)
        return new User(email, token, name);
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
