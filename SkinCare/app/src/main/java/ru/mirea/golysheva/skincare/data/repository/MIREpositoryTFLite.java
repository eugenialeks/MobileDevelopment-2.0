package ru.mirea.golysheva.skincare.data.repository;

import java.util.Random;
import ru.mirea.golysheva.skincare.domain.models.SkinTypeResult;
import ru.mirea.golysheva.skincare.domain.repository.MIRepository;

public class MIREpositoryTFLite implements MIRepository {
    private final Random rnd = new Random();

    @Override
    public SkinTypeResult detectSkinType(byte[] imageBytes) {
        String[] types = {"Нормальная","Сухая","Жирная","Комбинированная","Чувствительная"};
        String t = types[rnd.nextInt(types.length)];
        float score = 0.6f + rnd.nextFloat() * 0.4f;
        return new SkinTypeResult(t, score);
    }
}