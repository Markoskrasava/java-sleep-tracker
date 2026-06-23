package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    public static void main(String[] args) {
        String filepath = " ";
        List<SleepingSession> sessions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            sessions = reader.lines()
            .map(SleepingSession::transformString)
            .toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        final List<SleepingSession> finalSessions = sessions;
        List<Function> functions = List.of(new BadQualitySessionsFunction(),
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
}