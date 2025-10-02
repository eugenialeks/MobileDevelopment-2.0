package ru.mirea.golysheva.skincare.domain.repository;

import ru.mirea.golysheva.skincare.domain.models.SkinTypeResult;

public interface MIRepository {
    SkinTypeResult detectSkinType(byte[] imageBytes);
}
