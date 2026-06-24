package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите путь к файлу с логом сна");
            System.out.println("Пример: java SleepTrackerApp sleep_log.txt");
            return;
        }

        String filepath = args[0];
        List<SleepingSession> sessions = readFile(filepath);
        if (sessions.isEmpty()) {
            System.out.println("Файл не содержит данных о сне.");
            System.out.println("Проверьте путь: " + filepath);
        }

        final List<SleepingSession> finalSessions = sessions;
        List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = List.of(new BadQualitySessionsFunction(),
                new MaxSessionFunction(),
                new MinSessionFunction(),
                new TotalSessionsFunction(),
                new AverageSessionFunction(),
                new NightsWithoutSleeping(),
                new SleepingTypeFunction()
                );

        functions.stream()
        .map(f -> f.apply(finalSessions))
        .forEach(System.out::println);
    }

    private static List<SleepingSession> readFile(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            return reader.lines()
                    .map(SleepingSession::transformString)
                    .toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }
}