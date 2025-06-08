package com.gotrack.gotrack_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column; // Consider replacing
import jakarta.persistence.Entity; // Added
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Added
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "artist")
// Exclude relationship fields from equals and hashCode to prevent infinite recursion
@EqualsAndHashCode(exclude = {"bands", "albums", "tracks", "genres"})
// Exclude relationship fields from toString to prevent infinite recursion
@ToString(exclude = {"bands", "albums", "tracks", "genres"})
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String profileImage; // Consider renaming to image
    private String dateOfBirth;
    private String dateOfDeath;
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String biography;

    // Many-to-many: artist can join multiple bands, band has many artists
    // This is the inverse side, relationship owned by Band
    @ManyToMany(mappedBy = "members")
    private Set<Band> bands = new HashSet<>(); // Initialize collection

    // One-to-many: artist's solo albums
    // This is the parent side, relationship owned by Album
    @OneToMany(mappedBy = "artistOwner")
    private Set<Album> albums = new HashSet<>(); // Initialize collection

    // One-to-many: artist's solo tracks
    // This is the parent side, relationship owned by Track
    @OneToMany(mappedBy = "artist")
    private Set<Track> tracks = new HashSet<>(); // Initialize collection

    // Many-to-many: artist's genres
    // This is the owning side, JPA will create/manage the join table
    @ManyToMany
    @JoinTable(
        name = "artist_genre", // Join table name
        joinColumns = @JoinColumn(name = "artist_id"), // FK in join table referencing Artist
        inverseJoinColumns = @JoinColumn(name = "genre_id") // FK in join table referencing Genre
    )
    private Set<Genre> genres = new HashSet<>(); // Initialize collection

    
}