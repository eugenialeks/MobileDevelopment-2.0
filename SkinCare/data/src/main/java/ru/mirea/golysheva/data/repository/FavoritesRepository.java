package ru.mirea.golysheva.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FavoritesRepository {

    private static final String PREFS = "fav_prefs";
    private static final String KEY_IDS = "fav_ids";

    private final SharedPreferences sp;

    public interface IdsCallback { void onResult(Set<String> ids); }

    public FavoritesRepository(Context ctx) {
        this.sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    /** Синхронно прочитать набор id */
    private Set<String> read() {
        // Всегда возвращаем копию, иначе StringSet из SharedPreferences нельзя модифицировать напрямую
        return new HashSet<>(sp.getStringSet(KEY_IDS, Collections.emptySet()));
    }

    /** Сохранить набор id */
    private void write(Set<String> ids) {
        sp.edit().putStringSet(KEY_IDS, ids).apply();
    }

    /** Асинхронная обёртка, как у тебя использовалось раньше */
    public void getAll(IdsCallback cb) {
        cb.onResult(read());
    }

    /** Проверка, есть ли товар в избранном */
    public boolean contains(String id) {
        return read().contains(id);
    }

    /**
     * Переключить состояние избранного.
     * @return true — стал избранным; false — убран из избранного.
     */
    public boolean toggle(String id) {
        Set<String> ids = read();
        boolean nowFav;
        if (ids.contains(id)) {
            ids.remove(id);
            nowFav = false;
        } else {
            ids.add(id);
            nowFav = true;
        }
        write(ids);
        return nowFav;
    }
}
