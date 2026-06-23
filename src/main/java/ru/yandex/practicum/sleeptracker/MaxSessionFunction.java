package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long maxSession = sessions.stream()
                .mapToLong(s  -> s.getDuration().toMinutes())
                .max()
        .orElse(0);
        return new SleepAnalysisResult("Максимальное количество сна в сессии", maxSession);
    }
}
