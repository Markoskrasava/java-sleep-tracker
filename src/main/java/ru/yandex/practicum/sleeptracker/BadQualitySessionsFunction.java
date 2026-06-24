package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getSleepingQuality() == SleepingQuality.BAD)
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим сном", count);
    }
}
