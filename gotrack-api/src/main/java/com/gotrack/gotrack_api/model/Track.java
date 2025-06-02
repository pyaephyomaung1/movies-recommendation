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

     // --- Relationship Management Methods ---

    /**
     * Sets the album this track belongs to.
     * This is the primary method for managing the track-album relationship.
     * This method updates both sides of the relationship.
     *
     * @param album The album to set (or null to clear)
     * @return this track (for method chaining)
     */
    public Track setAlbum(Album album) {
        // If there's a previous album AND its tracks collection is initialized, remove this track from its collection
        if (this.album != null && this.album.getTracks() != null) {
            this.album.getTracks().remove(this);
        }

        this.album = album; // Set the new album

        // If the new album is not null AND its tracks collection is initialized, add this track to its collection
        if (album != null && album.getTracks() != null) {
            // Check if already present to avoid duplicates if List behaves unexpectedly
            if (!album.getTracks().contains(this)) {
                 album.getTracks().add(this);
            }
        }
        return this;
    }

    /**
     * Sets the artist owner of this track.
     * A track can be owned by either an artist OR a band, not both.
     * This is the primary method for managing the track-artist relationship.
     * This method updates both sides of the relationship and enforces mutual exclusivity.
     *
     * @param artist The artist who owns this track (or null to clear)
     * @return this track (for method chaining)
     */
    public Track setArtist(Artist artist) {
         // If there's a previous artist owner AND their collection is initialized, remove this track from their collection
        if (this.artist != null && this.artist.getTracks() != null) {
            this.artist.getTracks().remove(this);
        }

        this.artist = artist; // Set the new artist

        // If the new artist is not null AND their tracks collection is initialized, add this track to their collection
        if (artist != null && artist.getTracks() != null) {
            artist.getTracks().add(this);

            // If the track has a band owner, clear it (a track can't have both)
            if (this.band != null) {
                 // Remove from band's collection (if initialized)
                if (this.band.getTracks() != null) {
                    this.band.getTracks().remove(this);
                }
                // Clear the band owner reference
                this.band = null;
            }
        }
        return this;
    }

    /**
     * Sets the band owner of this track.
     * A track can be owned by either a band OR an artist, not both.
     * This is the primary method for managing the track-band relationship.
     * This method updates both sides of the relationship and enforces mutual exclusivity.
     *
     * @param band The band who owns this track (or null to clear)
     * @return this track (for method chaining)
     */
    public Track setBand(Band band) {
        // If there's a previous band owner AND their collection is initialized, remove this track from their collection
        if (this.band != null && this.band.getTracks() != null) {
            this.band.getTracks().remove(this);
        }

        this.band = band; // Set the new band

        // If the new band is not null AND their tracks collection is initialized, add this track to its collection
        if (band != null && band.getTracks() != null) {
            band.getTracks().add(this);

            // If the track has an artist owner, clear it (a track can't have both)
            if (this.artist != null) {
                 // Remove from artist's collection (if initialized)
                if (this.artist.getTracks() != null) {
                    this.artist.getTracks().remove(this);
                }
                // Clear the artist owner reference
                this.artist = null;
            }
        }
        return this;
    }

     /**
     * Adds a genre to this track and updates the other side.
     * This track owns the Many-to-Many relationship with Genre.
     * @param genre The genre to add
     * @return this track (for method chaining)
     */
    public Track addGenre(Genre genre) {
        if (genre != null) {
            this.genres.add(genre); // Add to Track's genres collection (owning side)
            // Add Track to Genre's tracks collection (inverse side)
            if (genre.getTracks() != null) { // Safety check for inverse side collection
                genre.getTracks().add(this);
            }
        }
        return this;
    }

    /**
     * Removes a genre from this track and updates the other side.
     * @param genre The genre to remove
     * @return this track (for method chaining)
     */
    public Track removeGenre(Genre genre) {
        // Check if genre is not null AND is currently in our collection
        if (genre != null && this.genres.contains(genre)) {
            this.genres.remove(genre); // Remove from Track's genres collection
             // Remove Track from Genre's tracks collection (inverse side)
             if (genre.getTracks() != null) { // Safety check for inverse side collection
                genre.getTracks().remove(this);
            }
        }
        return this;
    }
    // You can add bulk add/remove methods here as well
}