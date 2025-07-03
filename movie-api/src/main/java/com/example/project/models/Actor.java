package com.example.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private LocalDate dateOfDeath;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column
    private String profileImage;

    public boolean isAlive() {
        return dateOfDeath == null;
    }

    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }

        LocalDate endDate = isAlive() ? LocalDate.now() : dateOfDeath;
        return Period.between(dateOfBirth, endDate).getYears();
    }

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    private Set<Series> series = new HashSet<>();
}
