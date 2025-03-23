package com.ultraplexcinema.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Movie {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String director;

    private String ageRating;

    private long movieLengthInMinutes;

    //... other metadata ...

    // Auto generated equals and hashcode due to issue with lombok and JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Movie movie)) return false;

        return new EqualsBuilder().append(getId(), movie.getId()).append(getTitle(), movie.getTitle()).append(getDirector(), movie.getDirector()).append(getAgeRating(), movie.getAgeRating()).append(getMovieLengthInMinutes(), movie.getMovieLengthInMinutes()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getTitle()).append(getDirector()).append(getAgeRating()).append(getMovieLengthInMinutes()).toHashCode();
    }
}
