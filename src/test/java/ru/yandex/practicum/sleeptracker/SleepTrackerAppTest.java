package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SleepTrackerAppTest {
    private List<SleepingSession> sessions;
    private List<SleepingSession> listWithoutSessions;

    @BeforeEach
    void prepareForEveryFunctionTests() {
            sessions = List.of(SleepingSession.transformString("01.10.25 22:15;02.10.25 08:00;GOOD"),
                    SleepingSession.transformString("02.10.25 23:00;03.10.25 08:00;NORMAL"),
                    SleepingSession.transformString("03.10.25 14:30;03.10.25 15:20;NORMAL"),
                    SleepingSession.transformString("03.10.25 23:30;04.10.25 06:20;BAD"));

            listWithoutSessions = List.of();
    }

    @Test
    void testAverageSessionFunction() {
        AverageSessionFunction function = new AverageSessionFunction();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(396L, result.getValue());
    }

    @Test
    void testAverageSessionFunctionWithoutSessions() {
        AverageSessionFunction function = new AverageSessionFunction();
        SleepAnalysisResult result = function.apply(listWithoutSessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    void testBadQualityFunction() {
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(1L, result.getValue());
    }

    @Test
    void testBadQualityFunctionWithoutSessions() {
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.apply(listWithoutSessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    void testMaxSessionFunction() {
        MaxSessionFunction function = new MaxSessionFunction();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(585L, result.getValue());
    }

    @Test
    void testMaxSessionFunctionWithoutSessions() {
        MaxSessionFunction function = new MaxSessionFunction();
        SleepAnalysisResult result = function.apply(listWithoutSessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    void testMinSessionFunction() {
        MinSessionFunction function = new MinSessionFunction();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(50L, result.getValue());
    }

    @Test
    void testMinSessionFunctionWithoutSessions() {
        MinSessionFunction function = new MinSessionFunction();
        SleepAnalysisResult result = function.apply(listWithoutSessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    void testNightsWithoutSleepingNull() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.transformString("01.10.25 22:15;02.10.25 08:00;GOOD"),
                SleepingSession.transformString("02.10.25 23:00;03.10.25 08:00;NORMAL")
        );
        NightsWithoutSleeping function = new NightsWithoutSleeping();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    void testNightsWithoutSleeping() {
        List<SleepingSession> sleepSessions = List.of(
                SleepingSession.transformString("01.10.25 22:15;02.10.25 08:00;GOOD"),
                SleepingSession.transformString("02.10.25 23:00;03.10.25 08:00;NORMAL"),
                SleepingSession.transformString("03.10.25 14:30;03.10.25 15:20;NORMAL"),
                SleepingSession.transformString("03.10.25 23:30;04.10.25 06:20;BAD"),
                SleepingSession.transformString("05.10.25 22:00;06.10.25 07:00;GOOD")
        );
        NightsWithoutSleeping function = new NightsWithoutSleeping();
        SleepAnalysisResult result = function.apply(sleepSessions);
        Assertions.assertEquals(1L, result.getValue());
    }

    @Test
    void testAllNightsSleepless() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.transformString("01.10.25 10:00;01.10.25 12:00;NORMAL"),
                SleepingSession.transformString("02.10.25 14:00;02.10.25 16:00;NORMAL"),
                SleepingSession.transformString("03.10.25 08:00;03.10.25 10:00;NORMAL")
        );
        NightsWithoutSleeping function = new NightsWithoutSleeping();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(3L, result.getValue());
    }

    @Test
    void testForNextMonthTransition() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.transformString("28.09.25 22:00;29.09.25 08:00;GOOD"),
                SleepingSession.transformString("01.10.25 23:00;02.10.25 08:00;GOOD"),
                SleepingSession.transformString("02.10.25 23:00;03.10.25 08:00;GOOD"),
                SleepingSession.transformString("04.10.25 22:00;05.10.25 08:00;GOOD")
        );
        NightsWithoutSleeping function = new NightsWithoutSleeping();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(3L, result.getValue());
    }

    @Test
    void testMostlyOwl() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.transformString("01.10.25 23:30;02.10.25 09:30;GOOD"),
                SleepingSession.transformString("02.10.25 23:15;03.10.25 09:15;NORMAL"),
                SleepingSession.transformString("03.10.25 21:30;04.10.25 06:30;GOOD"),
                SleepingSession.transformString("04.10.25 22:30;05.10.25 08:00;GOOD")
        );

        SleepingTypeFunction function = new SleepingTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(SleepingType.OWL, result.getValue());
    }

    @Test
    void testPigeonChronotype() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.transformString("01.10.25 22:30;02.10.25 08:00;GOOD"),
                SleepingSession.transformString("02.10.25 21:30;03.10.25 06:30;NORMAL"),
                SleepingSession.transformString("03.10.25 23:30;04.10.25 09:30;GOOD"),
                SleepingSession.transformString("04.10.25 22:00;05.10.25 07:30;BAD")
        );

        SleepingTypeFunction function = new SleepingTypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        Assertions.assertEquals(SleepingType.PIGEON, result.getValue());
    }
}