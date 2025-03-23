package com.ultraplexcinema.repository;


import com.ultraplexcinema.model.entities.MovieBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieBookingRepository extends JpaRepository<MovieBooking, UUID> {
}
