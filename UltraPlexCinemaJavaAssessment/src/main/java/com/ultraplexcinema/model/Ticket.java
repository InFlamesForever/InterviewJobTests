package com.ultraplexcinema.model;

import java.time.LocalDateTime;

public record Ticket(
        String id,
        String screeningId,
        String movieTitle,
        LocalDateTime movieStartDateTime,
        long movieLengthMinutes
) {
}
