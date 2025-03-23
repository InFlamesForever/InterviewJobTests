package com.ultraplexcinema.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
public class MovieBooking {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonBackReference("booking")
    private Screening screening;

    // ... metadata ...

    // Auto generated equals and hashcode due to issue with lombok and JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MovieBooking that)) return false;

        return new EqualsBuilder().append(getId(), that.getId()).append(getScreening(), that.getScreening()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getScreening()).toHashCode();
    }
}
