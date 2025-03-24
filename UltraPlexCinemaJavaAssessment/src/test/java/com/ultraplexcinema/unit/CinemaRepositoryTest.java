package com.ultraplexcinema.unit;

import com.ultraplexcinema.model.entities.Cinema;
import com.ultraplexcinema.repository.CinemaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Simple test to show I know this can be done. Never sure if this counts as an integration test or unit as it uses H2 DB under the hood.
 * Closer to unit in this example I think though.
 * Cleanup would be needed between tests if more than one was here.
 */
@DataJpaTest
class CinemaRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    void shouldSaveCinema() {
        Cinema cinema = new Cinema();
        cinema.setName("UltraPlex London");
        cinema.setLocation("London, UK");

        Cinema savedCinema = cinemaRepository.save(cinema);

        assertNotNull(savedCinema.getId());
        assertEquals(cinema.getName(), savedCinema.getName());
        assertEquals(cinema.getLocation(), savedCinema.getLocation());
    }
}