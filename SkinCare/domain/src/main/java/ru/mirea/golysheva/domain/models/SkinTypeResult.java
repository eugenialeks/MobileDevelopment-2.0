package ru.mirea.golysheva.domain.models;

public class SkinTypeResult {
    private final String type;
    private final float score;

    public SkinTypeResult(String type, float score) {
        this.type = type;
        this.score = score;
    }
    public String getType() { return type; }
    public float getScore() { return score; }
}
