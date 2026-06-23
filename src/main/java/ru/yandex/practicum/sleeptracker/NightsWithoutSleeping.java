package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NightsWithoutSleeping implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        // 1. Находим ночи со сном
        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartTime();
                    return start.isAfter(LocalTime.of(19, 59)) || start.isBefore(LocalTime.of(6, 0));
                })
                .map(s -> {
                    LocalDate date = s.getStart().toLocalDate();
                    int startHour = s.getStart().getHour();
                    return startHour >= 12 ? date : date.minusDays(1);
                })
                .collect(Collectors.toSet());

        // 2. Находим период только по датам НАЧАЛА сессий
        LocalDate startDate = sessions.stream()
                .map(s -> s.getStart().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate endDate = sessions.stream()
                .map(s -> s.getStart().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow();

        // 3. Все ночи в периоде
        Set<LocalDate> allNights = new HashSet<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            allNights.add(current);
            current = current.plusDays(1);
        }

        // 4. Бессонные ночи
        allNights.removeAll(nightsWithSleep);

        return new SleepAnalysisResult("Количество бессонных ночей", (long) allNights.size());
    }
}
