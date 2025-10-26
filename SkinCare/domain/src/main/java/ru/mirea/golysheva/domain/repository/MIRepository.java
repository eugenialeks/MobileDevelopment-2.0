package ru.mirea.golysheva.domain.repository;

import ru.mirea.golysheva.domain.models.SkinTypeResult;

public interface MIRepository {
    SkinTypeResult detectSkinType(byte[] imageBytes);
}
