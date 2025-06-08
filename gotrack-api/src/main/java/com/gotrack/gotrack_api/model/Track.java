package com.gotrack.gotrack_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity; // Consider replacing
import jakarta.persistence.GeneratedValue; // Added
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Added
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // Consider replacing with @Getter, @Setter, @ToString.Exclude, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "track")
// Exclude relationship fields from equals and hashCode to prevent infinite recursion
@EqualsAndHashCode(exclude = {"album", "artist", "band", "genres"})
// Exclude relationship fields from toString to prevent infinite recursion
@ToString(exclude = {"album", "artist", "band", "genres"})
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String audioFile; // Consider renaming to mp3File

    // Many-to-one: track in album (can be null for a standalone single)
    // Track owns this relationship (FK album_id in track table)
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = true) // FK column name in the track table
    private Album album;

    // Many-to-one: track owner - can be a solo artist OR a band (nullable, handle via business logic)
    // Track owns this relationship (FK artist_id in track table)
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = true) // FK column name in the track table
    private Artist artist; // Artist owner

    // Many-to-one: track owned by one Band (mutual exclusivity handled by logic)
    // Track owns this relationship (FK band_id in track table)
    @ManyToOne
    @JoinColumn(name = "band_id", nullable = true) // FK column name in the track table
    private Band band; // Band owner

    // Many-to-many: genres
    // This is the owning side, JPA will create/manage the join table
    @ManyToMany
    @JoinTable(
        name = "track_genre", // Join table name
        joinColumns = @JoinColumn(name = "track_id"), // FK in join table referencing Track
        inverseJoinColumns = @JoinColumn(name = "genre_id") // FK in join table referencing Genre
    )
    private Set<Genre> genres = new HashSet<>(); // Initialize collection

}