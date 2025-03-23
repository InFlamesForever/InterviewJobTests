package com.ultraplexcinema;

import com.ultraplexcinema.model.entities.Cinema;
import com.ultraplexcinema.repository.CinemaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
// TODO: this, one for controller, one full integration test using docker runner maybe
@DataJpaTest
class CinemaRepositoryTests {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    void shouldSaveCinema() {
        Cinema cinema = new Cinema();
        cinema.setName("UltraPlex London");
        cinema.setLocation("London, UK");

        Cinema savedCinema = cinemaRepository.save(cinema);

        assertThat(savedCinema.getId()).isNotNull();
        assertThat(savedCinema.getName()).isEqualTo("UltraPlex London");
    }
}