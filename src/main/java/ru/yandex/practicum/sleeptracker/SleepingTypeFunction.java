package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class SleepingTypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult>  {
    private final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private final LocalTime OWL_SLEEP_END = LocalTime.of(9, 0);
    private final LocalTime LARK_SLEEP_START = LocalTime.of(22, 0);
    private final LocalTime LARK_SLEEP_END = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", SleepingType.PIGEON);
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartTime();
                    return start.isAfter(LocalTime.of(19, 59)) || start.isBefore(LocalTime.of(6, 0));
                })
                .toList();

        if (nightSessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", SleepingType.PIGEON);
        }

        // Считаем сов
        long owlCount = nightSessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartTime();
                    LocalTime end = s.getEndTime();
                    return start.isAfter(OWL_SLEEP_START) && end.isAfter(OWL_SLEEP_END);
                })
                .count();

        long larkCount = nightSessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStartTime();
                    LocalTime end = s.getEndTime();
                    return start.isBefore(LARK_SLEEP_START) && end.isBefore(LARK_SLEEP_END);
                })
                .count();

        long pigeonCount = nightSessions.size() - owlCount - larkCount;

        SleepingType result;
        if (owlCount > larkCount && owlCount > pigeonCount) {
            result = SleepingType.OWL;
        } else if (larkCount > owlCount && larkCount > pigeonCount) {
            result = SleepingType.LARK;
        } else {
            result = SleepingType.PIGEON;
        }

        return new SleepAnalysisResult("Хронотип пользователя", result);
    }
}
