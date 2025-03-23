package com.ultraplexcinema.model.entities;

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
public class Cinema {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String location;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    @ToString.Exclude // Excluded for lazy loading purposes
    @JsonManagedReference("screen")
    private List<Screen> screens;


    // Auto generated equals and hashcode due to issue with lombok and JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Cinema cinema)) return false;

        return new EqualsBuilder().append(getId(), cinema.getId()).append(getName(), cinema.getName()).append(getLocation(), cinema.getLocation()).append(getScreens(), cinema.getScreens()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getName()).append(getLocation()).append(getScreens()).toHashCode();
    }
}
