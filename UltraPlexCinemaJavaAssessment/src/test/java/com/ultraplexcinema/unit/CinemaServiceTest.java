package com.ultraplexcinema.unit;

import com.ultraplexcinema.error.ScreeningFullException;
import com.ultraplexcinema.model.entities.Movie;
import com.ultraplexcinema.model.entities.MovieBooking;
import com.ultraplexcinema.model.entities.Screen;
import com.ultraplexcinema.model.entities.Screening;
import com.ultraplexcinema.repository.*;
import com.ultraplexcinema.service.CinemaService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Simple test to show I know how to use mocks.
 * Because I'm only mocking repositories this may be better as a JPA data test,
 * but I'm just trying to show the concept.
 */
public class CinemaServiceTest {

    private final ScreeningRepository mockScreeningRepo = mock(ScreeningRepository.class);
    private final MovieBookingRepository mockMovieBookingRepo = mock(MovieBookingRepository.class);

    private final CinemaService subject = new CinemaService(
            mock(CinemaRepository.class),
            mock(ScreenRepository.class),
            mock(MovieRepository.class),
            mockScreeningRepo,
            mockMovieBookingRepo
    );

    @Test
    public void bookMovieTicket_happyPath() {
        // Setup variables
        var exampleScreening = new Screening();
        exampleScreening.setId(UUID.randomUUID());
        var exampleMovie = new Movie();
        exampleScreening.setMovie(exampleMovie);
        exampleMovie.setMovieLengthInMinutes(120);
        exampleScreening.setMovieTitle("movie");
        exampleScreening.setScreeningDate(LocalDate.of(2025, 3, 24));
        exampleScreening.setStartTime(LocalTime.of(12, 0));
        exampleScreening.setBookings(Collections.emptyList());
        exampleScreening.setScreen(new Screen());
        exampleScreening.getScreen().setSeatCapacity(100);
        when(mockScreeningRepo.findById(exampleScreening.getId())).thenReturn(Optional.of(exampleScreening));

        var exampleMovieBooking = new MovieBooking();
        exampleMovieBooking.setId(UUID.randomUUID());
        exampleMovieBooking.setScreening(exampleScreening);

        var inputBooking = new MovieBooking();
        var inputScreening = new Screening();
        inputScreening.setId(exampleScreening.getId());
        inputBooking.setScreening(inputScreening);
        when(mockMovieBookingRepo.save(inputBooking)).thenReturn(exampleMovieBooking);

        // Run method
        var resultTicket = subject.bookMovieTicket(inputBooking);

        // verify results
        verify(mockScreeningRepo, times(1)).findById(exampleScreening.getId());
        verify(mockMovieBookingRepo, times(1)).save(inputBooking);

        assertNotNull(resultTicket);
        assertEquals(exampleMovieBooking.getId().toString(), resultTicket.id());
        assertEquals(exampleScreening.getId().toString(), resultTicket.screeningId());
        assertEquals(exampleScreening.getMovieTitle(), resultTicket.movieTitle());
        assertEquals(LocalDateTime.of(exampleScreening.getScreeningDate(), exampleScreening.getStartTime()), resultTicket.movieStartDateTime());
        assertEquals(exampleMovie.getMovieLengthInMinutes(), resultTicket.movieLengthMinutes());

    }

    @Test
    public void bookMovieTicket_unhappyPath_noCapacity() {
        // Setup variables
        var exampleScreening = new Screening();
        exampleScreening.setId(UUID.randomUUID());
        var exampleMovie = new Movie();
        exampleScreening.setMovie(exampleMovie);
        exampleMovie.setMovieLengthInMinutes(120);
        exampleScreening.setMovieTitle("movie");
        exampleScreening.setScreeningDate(LocalDate.of(2025, 3, 24));
        exampleScreening.setStartTime(LocalTime.of(12, 0));
        exampleScreening.setBookings(Collections.singletonList(new MovieBooking()));
        exampleScreening.setScreen(new Screen());
        exampleScreening.getScreen().setSeatCapacity(1);
        when(mockScreeningRepo.findById(exampleScreening.getId())).thenReturn(Optional.of(exampleScreening));

        var exampleMovieBooking = new MovieBooking();
        exampleMovieBooking.setId(UUID.randomUUID());
        exampleMovieBooking.setScreening(exampleScreening);

        var inputBooking = new MovieBooking();
        var inputScreening = new Screening();
        inputScreening.setId(exampleScreening.getId());
        inputBooking.setScreening(inputScreening);
        when(mockMovieBookingRepo.save(inputBooking)).thenReturn(exampleMovieBooking);

        // Run method
        var thrown = assertThrows(
                ScreeningFullException.class,
                () -> subject.bookMovieTicket(inputBooking),
                "Expected bookMovieTicket to throw an exception, but it didn't"
        );
        assertEquals("This screening of movie is full. Booking cancelled.", thrown.getMessage());

    }
}
