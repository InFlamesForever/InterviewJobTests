package com.ultraplexcinema.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Screen {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    @JsonBackReference("screen")
    private Cinema cinema;

    private String name;

    private int seatCapacity;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    @ToString.Exclude // Excluded for lazy loading purposes
    @JsonManagedReference("screening")
    private List<Screening> screenings;

    // Auto generated equals and hashcode due to issue with lombok and JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Screen screen)) return false;

        return new EqualsBuilder().append(getSeatCapacity(), screen.getSeatCapacity()).append(getId(), screen.getId()).append(getCinema(), screen.getCinema()).append(getName(), screen.getName()).append(getScreenings(), screen.getScreenings()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getCinema()).append(getName()).append(getSeatCapacity()).append(getScreenings()).toHashCode();
    }
}
