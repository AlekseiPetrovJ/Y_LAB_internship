package ru.petrov.model;

public enum LogLevel {
    FATAL(100000),
    WARN(100001),
    INFO(100002),
    DEBUG(100003);
    private final int levelId;

    private LogLevel(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelId() {
        return levelId;
    }
}
