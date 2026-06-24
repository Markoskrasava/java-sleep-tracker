package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NightsWithoutSleeping implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final LocalTime NIGHT_SLEEP_START = LocalTime.of(19, 59);
    public static final LocalTime NIGHT_SLEEP_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }
        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartTime();
                    return start.isAfter(NIGHT_SLEEP_START) || start.isBefore(NIGHT_SLEEP_END);
                })
                .map(s -> {
                    LocalDate date = s.getStart().toLocalDate();
                    int startHour = s.getStart().getHour();
                    return startHour >= 12 ? date : date.minusDays(1);
                })
                .collect(Collectors.toSet());

        LocalDate startDate = sessions.stream()
                .map(s -> s.getStart().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate endDate = sessions.stream()
                .map(s -> s.getStart().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow();

        Set<LocalDate> allNights = new HashSet<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            allNights.add(current);
            current = current.plusDays(1);
        }

        allNights.removeAll(nightsWithSleep);

        return new SleepAnalysisResult("Количество бессонных ночей", (long) allNights.size());
    }
}
