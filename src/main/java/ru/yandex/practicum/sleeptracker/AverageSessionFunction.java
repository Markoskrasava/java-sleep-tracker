package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
         long avg = (long) sessions.stream()
                .mapToLong(s  -> s.getDuration().toMinutes())
                .average()
                .orElse(0);
         return new SleepAnalysisResult("Среднее количество сессий (мин)", avg);

    }
}
