package com.ultraplexcinema.model;

import com.ultraplexcinema.model.entities.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningQueryResponse (
    String cinemaName,
    String screenName,
    Movie movie,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate screeningDate
){}
