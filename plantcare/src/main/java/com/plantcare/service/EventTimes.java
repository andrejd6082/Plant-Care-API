package com.plantcare.service;

import com.plantcare.exception.InvalidRequestException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/** Turns the optional client-supplied time of an event into the stored UTC time. */
final class EventTimes {

    // Phones' clocks can be a little ahead of the server's.
    private static final Duration CLOCK_TOLERANCE = Duration.ofMinutes(2);

    private EventTimes() {}

    /** The time the event happened: the client's time if given, otherwise now. */
    static LocalDateTime resolve(Instant happenedAt) {
        if (happenedAt == null) {
            return LocalDateTime.now(ZoneOffset.UTC);
        }
        if (happenedAt.isAfter(Instant.now().plus(CLOCK_TOLERANCE))) {
            throw new InvalidRequestException("The event time cannot be in the future.");
        }
        return LocalDateTime.ofInstant(happenedAt, ZoneOffset.UTC);
    }
}
