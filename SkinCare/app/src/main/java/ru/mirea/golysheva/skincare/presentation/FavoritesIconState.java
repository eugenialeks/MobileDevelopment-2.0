package ru.mirea.golysheva.skincare.presentation;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import ru.mirea.golysheva.data.repository.FavoritesRepository;

public class FavoritesIconState {
    private static final Set<String> cache = new HashSet<>();
    public static boolean cacheContains(Context c, String id) {
        if (cache.isEmpty()) {
            new FavoritesRepository(c).getAll(ids -> {
                cache.clear(); cache.addAll(ids);
            });
        }
        return cache.contains(id);
    }
    public static void invalidate(Context c) {
        new FavoritesRepository(c).getAll(ids -> { cache.clear(); cache.addAll(ids); });
    }
}