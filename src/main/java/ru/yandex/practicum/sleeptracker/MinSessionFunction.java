package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minSession = sessions.stream()
                .mapToLong(s  -> s.getDuration().toMinutes())
                .min()
                .orElse(0);
        return new SleepAnalysisResult("Минимальная продолжительность сна", minSession);
    }
}
