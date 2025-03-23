package com.ultraplexcinema.controller;

import com.ultraplexcinema.model.*;
import com.ultraplexcinema.model.entities.*;
import com.ultraplexcinema.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * This single controller contains all endpoints for the system.
 * Add, update and delete endpoints for all the cinemas needs as well as ticket bookings and changes.
 * In a larger system multiple controllers may make more sense and probably be more readable,
 * but for this example project I'm just writing a single controller for my own sake.
 * <p>
 * There is also no validation on input values as none was asked for,
 * I'm mentioning it so that you know it was considered.
 * </p>
 */
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    /**
     * Adds a new cinema to the system, no validation implemented, can add duplicates.
     * Should be authorised to admin level or equivalent.
     *
     * @param cinema - new cinema to add to the system
     * @return the saved cinema
     */
    @PostMapping("")
    public ResponseEntity<Cinema> add(
            @RequestBody Cinema cinema
    ) {
        var createdCinema = cinemaService.add(cinema);
        return ResponseEntity.ok(createdCinema);
    }

    /**
     * Updates an existing cinema to the system.
     * Should be authorised to admin level or equivalent.
     *
     * @param cinema - cinema to update
     * @return the updated cinema
     */
    @PatchMapping("")
    public ResponseEntity<Cinema> update(
            @RequestBody Cinema cinema
    ) {
        var updated = cinemaService.update(cinema);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a cinema if it exists.
     * Should be authorised to admin level or equivalent.
     *
     * @param cinemaId the ID of the cinema to delete
     * @return success or error
     */
    @DeleteMapping("{cinemaId}")
    public ResponseEntity<String> delete(
            @PathVariable("cinemaId") UUID cinemaId
    ) {
        cinemaService.delete(cinemaId);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a screen to the system, no validation, duplicates valid.
     * Will not handle invalid requests well, would need more validation or error handling.
     * Should be authorised to admin level or equivalent.
     *
     * @param screen the screen to be added to a cinema
     * @return the screen added
     */
    @PostMapping("screen")
    public ResponseEntity<Screen> addScreen(
            @RequestBody Screen screen
    ) {
        var createdScreen = cinemaService.addScreen(screen);
        return ResponseEntity.ok(createdScreen);
    }

    // Intentionally left out update and delete screen methods to reduce time spent

    /**
     * Adds a movie to the system. No validation, duplicates are valid.
     * Should be authorised to director or area director operations level.
     *
     * @param movie the movie to be added
     * @return the added movie
     */
    @PostMapping("movie")
    public ResponseEntity<Movie> addMovie(
            @RequestBody Movie movie
    ) {
        var createdMovie = cinemaService.addMovie(movie);
        return ResponseEntity.ok(createdMovie);
    }

    // Intentionally left out update and delete movie methods to reduce time spent

    /**
     * Adds a screening of a movie to a screen in a cinema. There should be validation to prevent overlap of screenings,
     * this has been left out, as well as other validation of input.
     * Should be authorised to individual cinema manager level.
     *
     * @param screening the screening added
     * @return the screening
     */
    @PostMapping("screening")
    public ResponseEntity<Screening> addScreening(
            @RequestBody Screening screening
    ) {
        var scheduledScreening = cinemaService.addScreening(screening);
        return ResponseEntity.ok(scheduledScreening);
    }

    // Intentionally left out update and delete screening methods to reduce time spent

    /**
     * Books a movie ticket for a specific screenings. Will validate that the screening isn't over capacity.
     * Should be restricted to customers or regular staff depending on business needs of the system.
     *
     * @param booking the details of the booking
     * @return the movie ticket
     */
    @PostMapping("movieTicket")
    public ResponseEntity<Ticket> bookMovieTicket(
            @RequestBody MovieBooking booking
    ) {
        var ticket = cinemaService.bookMovieTicket(booking);
        return ResponseEntity.ok(ticket);
    }

    // Intentionally left out update and delete ticket methods to reduce time spent

    /**
     * Will get all cinemas in the system.
     * Should be restricted to at least manager level.
     *
     * @return all cinemas in the system.
     */
    @GetMapping("all")
    public List<Cinema> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    /**
     * Gets all screenings at a cinema for a date.
     * Doesn't really need restrictions of access, could be public access.
     *
     * @param cinemaId ID of the cinema
     * @param screeningDate date to check for screenings
     * @param sortByStartTime whether to sort by screening start time
     * @param sortByMovieName whether to sort by movie name, start time has priority on sort
     * @return screenings on given date at specified cinema
     */
    @GetMapping("{cinemaId}/screeningsForDate")
    public ResponseEntity<List<ScreeningQueryResponse>> getAllScreeningsForCinemaOnDate(
            @PathVariable("cinemaId") UUID cinemaId,
            @RequestParam("screeningDate") LocalDate screeningDate,
            @RequestParam("sortByStartTime") boolean sortByStartTime,
            @RequestParam("sortByMovieName") boolean sortByMovieName
    ) {
        var screeningsFound = cinemaService.getAllScreeningsForCinemaOnDate(cinemaId, screeningDate, sortByStartTime, sortByMovieName);
        return ResponseEntity.ok(screeningsFound);
    }

    /**
     * Gets all screenings of a movie on a given date.
     * Doesn't really need restrictions of access, could be public access.
     *
     * @param movieId the id of the movie to search for
     * @param screeningDate the date to search for
     * @return the screenings found, unordered
     */
    @GetMapping("/movie/{movieId}/forDate")
    public ResponseEntity<List<ScreeningQueryResponse>> getAllScreeningsForMovieOnDate(
            @PathVariable("movieId") UUID movieId,
            @RequestParam("screeningDate") LocalDate screeningDate
    ) {
        var screeningsFound =  cinemaService.getAllScreeningsForMovieOnDate(movieId, screeningDate);
        return ResponseEntity.ok(screeningsFound);
    }

    // More query ideas...
    @GetMapping("movies")
    public List<Cinema> getAllMovies() {
        throw new UnsupportedOperationException("Not implemented to save time. Would be a paginated list of all movies sorted by name");
    }
    @GetMapping("screenings")
    public List<Cinema> getAllScreenings() {
        throw new UnsupportedOperationException("Not implemented to save time. Would be a paginated list of all screenings sorted by name");
    }

}
