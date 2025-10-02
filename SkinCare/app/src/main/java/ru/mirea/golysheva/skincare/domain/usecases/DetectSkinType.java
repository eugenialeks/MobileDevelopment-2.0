package ru.mirea.golysheva.skincare.domain.usecases;

import ru.mirea.golysheva.skincare.domain.models.SkinTypeResult;
import ru.mirea.golysheva.skincare.domain.repository.MIRepository;

public class DetectSkinType {
    private final MIRepository repo;
    public DetectSkinType(MIRepository repo) { this.repo = repo; }
    public SkinTypeResult execute(byte[] imageBytes) {
        return repo.detectSkinType(imageBytes);
    }
}