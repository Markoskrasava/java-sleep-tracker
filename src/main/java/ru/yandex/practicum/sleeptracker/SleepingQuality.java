package ru.yandex.practicum.sleeptracker;

public enum SleepingQuality {
    GOOD,
    NORMAL,
    BAD;

    public static SleepingQuality transformQuality(String value) {
        return switch (value) {
            case "GOOD" -> GOOD;
            case "NORMAL" -> NORMAL;
            case "BAD" -> BAD;
            default -> throw new IllegalArgumentException("Неизвестное качество сна: " + value);
        };
    }
}
