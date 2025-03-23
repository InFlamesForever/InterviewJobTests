package com.ultraplexcinema.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ultraplexcinema.model.ScreeningQueryResponse;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Screening {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    @JsonBackReference("cinema")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonBackReference("screening")
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    @ToString.Exclude // Excluded for lazy loading purposes
    @JsonManagedReference("booking")
    private List<MovieBooking> bookings;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate screeningDate;

    // This is duplicated data, but it's used to make my life easier so that I can generate the JPA query with just a name.
    private String movieTitle;


    // Auto generated equals and hashcode due to issue with lombok and JPA entities

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Screening screening)) return false;

        return new EqualsBuilder().append(getId(), screening.getId()).append(getCinema(), screening.getCinema()).append(getScreen(), screening.getScreen()).append(getMovie(), screening.getMovie()).append(getBookings(), screening.getBookings()).append(getStartTime(), screening.getStartTime()).append(getEndTime(), screening.getEndTime()).append(getScreeningDate(), screening.getScreeningDate()).append(getMovieTitle(), screening.getMovieTitle()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getCinema()).append(getScreen()).append(getMovie()).append(getBookings()).append(getStartTime()).append(getEndTime()).append(getScreeningDate()).append(getMovieTitle()).toHashCode();
    }

    public static ScreeningQueryResponse screeningToQueryResponse(Screening screening) {
        return new ScreeningQueryResponse(
                screening.getCinema().getName(),
                screening.getScreen().getName(),
                screening.getMovie(),
                screening.getStartTime(),
                screening.getEndTime(),
                screening.getScreeningDate()
        );
    }
}
