package com.example.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int seasonNumber;

    @Column
    private LocalDate releasedDate;

    @Column
    private String poster;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "season")
    private Set<Episode> episodes = new HashSet<>();
}
