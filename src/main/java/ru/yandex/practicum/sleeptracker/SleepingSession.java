package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private LocalDateTime start;
    private LocalDateTime end;
    private SleepingQuality sleepingQuality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepingQuality sleepingQuality) {
        this.start = start;
        this.end = end;
        this.sleepingQuality = sleepingQuality;
    }

    public static SleepingSession transformString(String line) {
        String[] lines = line.split(";");
        LocalDateTime start = LocalDateTime.parse(lines[0], formatter);
        LocalDateTime end = LocalDateTime.parse(lines[1], formatter);
        SleepingQuality sleepingQuality = SleepingQuality.transformQuality(lines[2]);
        return new SleepingSession(start, end, sleepingQuality);
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
    }

    public SleepingQuality getSleepingQuality() {
        return sleepingQuality;
    }
}
