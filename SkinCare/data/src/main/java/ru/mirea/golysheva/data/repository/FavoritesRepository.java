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

    private Set<String> read() {
        return new HashSet<>(sp.getStringSet(KEY_IDS, Collections.emptySet()));
    }

    private void write(Set<String> ids) {
        sp.edit().putStringSet(KEY_IDS, ids).apply();
    }

    public void getAll(IdsCallback cb) {
        cb.onResult(read());
    }

    public boolean contains(String id) {
        return read().contains(id);
    }

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
