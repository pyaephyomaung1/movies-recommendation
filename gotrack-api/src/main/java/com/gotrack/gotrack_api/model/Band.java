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
@Table(name = "band")
// Exclude relationship fields from equals and hashCode to prevent infinite recursion
@EqualsAndHashCode(exclude = {"members", "albums", "tracks", "genres"})
// Exclude relationship fields from toString to prevent infinite recursion
@ToString(exclude = {"members", "albums", "tracks", "genres"})
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bandName;
    private String bandCover;
    private String activeYear;
    private String origin;
    @Column(columnDefinition="TEXT")
    private String overview;

    // Many-to-many: band members
    // This is the owning side, JPA will create/manage the join table
    @ManyToMany
    @JoinTable(
        name = "band_members", // Join table name
        joinColumns = @JoinColumn(name = "band_id"), // FK in join table referencing Band
        inverseJoinColumns = @JoinColumn(name = "artist_id") // FK in join table referencing Artist
    )
    private Set<Artist> members  = new HashSet<>(); // Initialize collection

    // One-to-many: albums released by the band
    // This is the parent side, relationship owned by Album
    @OneToMany(mappedBy = "bandOwner")
    private Set<Album> albums  = new HashSet<>(); // Initialize collection

    // One-to-many: tracks released by the band (non-album singles, if any)
    // This is the parent side, relationship owned by Track
    @OneToMany(mappedBy = "band")
    private Set<Track> tracks  = new HashSet<>(); // Initialize collection

    // Many-to-many: band genres
    // This is the owning side, JPA will create/manage the join table
     @ManyToMany
    @JoinTable(
        name = "band_genre", // Join table name
        joinColumns = @JoinColumn(name = "band_id"), // FK in join table referencing Band
        inverseJoinColumns = @JoinColumn(name = "genre_id") // FK in join table referencing Genre
    )
    private Set<Genre> genres = new HashSet<>(); // Initialize collection

    
}