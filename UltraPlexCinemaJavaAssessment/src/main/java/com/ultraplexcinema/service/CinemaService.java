package com.ultraplexcinema.service;

import com.ultraplexcinema.error.CinemaNotFoundException;
import com.ultraplexcinema.error.ScreeningFullException;
import com.ultraplexcinema.model.*;
import com.ultraplexcinema.model.entities.*;
import com.ultraplexcinema.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * This service handles all the business logic. It's not feature complete, where things have been left out I have left a comment.
 */
@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieBookingRepository movieBookingRepository;

    public CinemaService(
            CinemaRepository cinemaRepository,
            ScreenRepository screenRepository,
            MovieRepository movieRepository,
            ScreeningRepository screeningRepository,
            MovieBookingRepository movieBookingRepository
    ) {
        this.cinemaRepository = cinemaRepository;
        this.screenRepository = screenRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.movieBookingRepository = movieBookingRepository;
    }

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema add(Cinema cinema) {
        // There would need to be protections against saving the same cinema twice,
        // these have been omitted to save time.
        return cinemaRepository.save(cinema);
    }

    public Cinema update(Cinema cinema) {
        var existingOptional = cinemaRepository.findById(cinema.getId());
        if (existingOptional.isEmpty()) {
            return null;
        }
        var existing = existingOptional.get();
        updateCinemaFields(cinema, existing);
        return cinemaRepository.save(existing);
    }

    private void updateCinemaFields(Cinema newCinema, Cinema existing) {
        var newName = newCinema.getName();
        if (newName != null) {
            existing.setName(newName);
        }
        var newLocation = newCinema.getLocation();
        if (newLocation != null) {
            existing.setLocation(newLocation);
        }
        var newScreens = newCinema.getScreens();
        if (newScreens != null && !newScreens.isEmpty()) {
            existing.setScreens(newScreens);
        }
    }

    public void delete(UUID cinemaId) {
        cinemaRepository.findById(cinemaId)
            .ifPresent(cinemaRepository::delete);
    }

    public Screen addScreen(Screen screen) {
        // There would need to be protections against saving the same screen twice,
        // these have been omitted to save time.
        return screenRepository.save(screen);
    }

    public Movie addMovie(Movie movie) {
        // There would need to be protections against saving the same movie twice,
        // these have been omitted to save time.
        return movieRepository.save(movie);
    }

    public Screening addScreening(Screening screening) {
        var movieOptional = movieRepository.findById(screening.getMovie().getId());
        if (movieOptional.isEmpty()) {
            return null;
        }
        var movie = movieOptional.get();

        screening.setMovieTitle(movie.getTitle());
        screening.setEndTime(screening.getStartTime().plusMinutes(movie.getMovieLengthInMinutes()));
        return screeningRepository.save(screening);
    }

    public Ticket bookMovieTicket(MovieBooking booking) {
        var screeningOptional = screeningRepository.findById(booking.getScreening().getId());
        if (screeningOptional.isEmpty()) {
            return null;
        }
        var screening = screeningOptional.get();
        if (screening.getBookings().size() + 1 > screening.getScreen().getSeatCapacity()) {
            // ** Note ** this error won't be handled properly, not a good user experience, can be improved.
            throw new ScreeningFullException("This screening of " + screening.getMovieTitle() + " is full. Booking cancelled.");
        }
        var savedBooking = movieBookingRepository.save(booking);

        return new Ticket(
                savedBooking.getId().toString(),
                screening.getId().toString(),
                screening.getMovie().getTitle(),
                LocalDateTime.of(
                        screening.getScreeningDate(),
                        screening.getStartTime()
                ),
                screening.getMovie().getMovieLengthInMinutes()
        );
    }

    public List<ScreeningQueryResponse> getAllScreeningsForCinemaOnDate(
            UUID cinemaId,
            LocalDate screeningDate,
            boolean sortByStartTime,
            boolean sortByMovieName
    ) {
        var screenings = getScreeningsForDate(cinemaId, screeningDate, sortByStartTime, sortByMovieName);
        return screenings.stream()
                .map(Screening::screeningToQueryResponse)
                .toList();
    }

    private List<Screening> getScreeningsForDate(UUID cinemaId, LocalDate screeningDate, boolean sortByStartTime, boolean sortByMovieName) {
        var cinemaOptional = cinemaRepository.findById(cinemaId);
        if (cinemaOptional.isEmpty()) {
            // ** Note ** this error won't be handled properly, not a good user experience, can be improved.
            throw new CinemaNotFoundException("The given cinema can't be found.");
        }
        var cinema = cinemaOptional.get();
        if (sortByStartTime) {
            return screeningRepository.findByCinemaAndScreeningDateOrderByStartTimeAsc(
                    cinema,
                    screeningDate
            );
        }

        if (sortByMovieName) {
            return screeningRepository.findByCinemaAndScreeningDateOrderByMovieTitleAsc(
                    cinema,
                    screeningDate
            );
        }

        return screeningRepository.findByCinemaAndScreeningDate(
                cinema,
                screeningDate
        );
    }

    public List<ScreeningQueryResponse> getAllScreeningsForMovieOnDate(UUID movieId, LocalDate screeningDate) {
        var movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            return Collections.emptyList();
        }
        var movie = movieOptional.get();
        return screeningRepository.findByMovieAndScreeningDate(movie, screeningDate)
                .stream()
                .map(Screening::screeningToQueryResponse)
                .toList();
    }


}
