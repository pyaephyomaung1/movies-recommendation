package com.gotrack.gotrack_api.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String audioFile;

    // Many-to-one: track in album (can be null for a standalone single)
    @ManyToOne
    private Album album;

    // Many-to-one: track owner - can be a solo artist or a band (nullable, handle via business logic)
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = true)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "band_id", nullable = true)
    private Band band;

    // Many-to-many: genres
    @ManyToMany
    private Set<Genre> genres;
}