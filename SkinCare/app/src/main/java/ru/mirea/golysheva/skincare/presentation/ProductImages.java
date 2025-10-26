package ru.mirea.golysheva.skincare.presentation;

import androidx.annotation.DrawableRes;

import ru.mirea.golysheva.skincare.R;

public final class ProductImages {

    @DrawableRes
    public static int of(String name) {
        if (name == null) return R.drawable.ic_placeholder;
        switch (name) {
            case "prod_cleanser": return R.drawable.prod_cleanser;
            case "prod_serum":    return R.drawable.prod_serum;
            case "prod_spf":      return R.drawable.prod_spf;
            default:              return R.drawable.ic_placeholder;
        }
    }

    private ProductImages() {}
}
