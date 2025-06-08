package com.gotrack.gotrack_api.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List; // Consider replacing
import java.util.Set; // Added

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; // Added
import jakarta.persistence.GenerationType; // Use ArrayList for List
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "album")
// Exclude relationship fields from equals and hashCode to prevent infinite recursion
@EqualsAndHashCode(exclude = {"tracks", "genres", "artistOwner", "bandOwner"})
// Exclude relationship fields from toString to prevent infinite recursion
@ToString(exclude = {"tracks", "genres", "artistOwner", "bandOwner"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String titleCover;
    private String releasedYear;

    // One-to-many: album tracks
    // This is the parent side, relationship owned by Track
    @OneToMany(mappedBy = "album")
    private List<Track> tracks = new ArrayList<>(); // Initialize collection (List to maintain order)

    // Many-to-many: album genres
    // This is the owning side, JPA will create/manage the join table
    @ManyToMany
    @JoinTable(
        name = "album_genre", // Join table name
        joinColumns = @JoinColumn(name = "album_id"), // FK in join table referencing Album
        inverseJoinColumns = @JoinColumn(name = "genre_id") // FK in join table referencing Genre
    )
    private Set<Genre> genres = new HashSet<>(); // Initialize collection

    // Many-to-one: Album can be owned by an artist OR a band, not both (mutual exclusivity handled by logic)
    // Album owns this relationship (FK artist_id in album table)
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = true) // FK column name in the album table
    private Artist artistOwner;

    // Many-to-one: Album can be owned by a band (mutual exclusivity handled by logic)
    // Album owns this relationship (FK band_id in album table)
    @ManyToOne
    @JoinColumn(name = "band_id", nullable = true) // FK column name in the album table
    private Band bandOwner;

       // You can add bulk add/remove methods here as well
}