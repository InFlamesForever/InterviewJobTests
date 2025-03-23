package com.ultraplexcinema.repository;


import com.ultraplexcinema.model.entities.Cinema;
import com.ultraplexcinema.model.entities.Movie;
import com.ultraplexcinema.model.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, UUID> {
    List<Screening> findByCinemaAndScreeningDate(
            Cinema cinema,
            LocalDate screeningDate
    );

    List<Screening> findByCinemaAndScreeningDateOrderByStartTimeAsc(
            Cinema cinema,
            LocalDate screeningDate
    );

    List<Screening> findByCinemaAndScreeningDateOrderByMovieTitleAsc(
            Cinema cinema,
            LocalDate screeningDate
    );

    List<Screening> findByMovieAndScreeningDate(
            Movie movie,
            LocalDate screeningDate
    );

}
