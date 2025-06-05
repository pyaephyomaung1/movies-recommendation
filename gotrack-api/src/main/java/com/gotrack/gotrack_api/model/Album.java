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

    // --- Relationship Management Methods ---

    /**
     * Sets the artist owner of this album.
     * This is the primary method for managing the album-artist relationship.
     * An album can be owned by either an artist OR a band, not both.
     * This method updates both sides of the relationship and enforces mutual exclusivity.
     *
     * @param artist The artist who owns this album (or null to clear)
     * @return this album (for method chaining)
     */
    public Album setArtistOwner(Artist artist) {
        // If there's a previous artist owner AND their collection is initialized, remove this album from their collection
        if (this.artistOwner != null && this.artistOwner.getAlbums() != null) {
            this.artistOwner.getAlbums().remove(this);
        }

        this.artistOwner = artist; // Set the new artist

        // If the new artist is not null AND their albums collection is initialized, add this album to their collection
        if (artist != null && artist.getAlbums() != null) {
            artist.getAlbums().add(this);

            // If the album also has a band owner, clear it (an album can't have both)
            if (this.bandOwner != null) {
                // Remove from band's collection (if initialized)
                if (this.bandOwner.getAlbums() != null) {
                     this.bandOwner.getAlbums().remove(this);
                }
                // Clear the band owner reference
                this.bandOwner = null;
            }
        }
        return this;
    }

    /**
     * Sets the band owner of this album.
     * This is the primary method for managing the album-band relationship.
     * An album can be owned by either a band OR an artist, not both.
     * This method updates both sides of the relationship and enforces mutual exclusivity.
     *
     * @param band The band who owns this album (or null to clear)
     * @return this album (for method chaining)
     */
    public Album setBandOwner(Band band) {
        // If there's a previous band owner AND their collection is initialized, remove this album from their collection
        if (this.bandOwner != null && this.bandOwner.getAlbums() != null) {
            this.bandOwner.getAlbums().remove(this);
        }

        this.bandOwner = band; // Set the new band

        // If the new band is not null AND their albums collection is initialized, add this album to their collection
        if (band != null && band.getAlbums() != null) {
            band.getAlbums().add(this);

            // If the album also has an artist owner, clear it (an album can't have both)
            if (this.artistOwner != null) {
                 // Remove from artist's collection (if initialized)
                 if (this.artistOwner.getAlbums() != null) {
                     this.artistOwner.getAlbums().remove(this);
                 }
                 // Clear the artist owner reference
                this.artistOwner = null;
            }
        }
        return this;
    }

    /**
     * Adds a track to this album.
     * This delegates to the Track's setter as Track owns the foreign key.
     * @param track The track to add
     * @return this album (for method chaining)
     */
    public Album addTrack(Track track) {
        if (track != null) {
            track.setAlbum(this); // Delegate to Track's setter (owning side)

            // Add to our collection (safety - should be handled by track.setAlbum)
            // Check if already present to avoid duplicates if List behaves unexpectedly
            if (!this.tracks.contains(track)) {
                 this.tracks.add(track);
            }
        }
        return this;
    }

    /**
     * Removes a track from this album.
     * Delegates to the Track's setter to clear the relationship.
     * @param track The track to remove
     * @return this album (for method chaining)
     */
    public Album removeTrack(Track track) {
        // Check if track is not null AND is currently in our collection
        if (track != null && this.tracks.contains(track)) {
            // Check if this album is the actual owner before clearing
             if (track.getAlbum() == this) {
                 track.setAlbum(null); // Delegate to Track's setter (owning side) to clear the foreign key
             }
            // Remove from our collection
            this.tracks.remove(track);
        }
        return this;
    }

    /**
     * Adds a genre to this album and updates the other side.
     * This album owns the Many-to-Many relationship with Genre.
     * @param genre The genre to add
     * @return this album (for method chaining)
     */
    public Album addGenre(Genre genre) {
        if (genre != null) {
            this.genres.add(genre); // Add to Album's genres collection (owning side)
            // Add Album to Genre's albums collection (inverse side)
            if (genre.getAlbums() != null) { // Safety check for inverse side collection
                genre.getAlbums().add(this);
            }
        }
        return this;
    }

    /**
     * Removes a genre from this album and updates the other side.
     * @param genre The genre to remove
     * @return this album (for method chaining)
     */
    public Album removeGenre(Genre genre) {
        // Check if genre is not null AND is currently in our collection
        if (genre != null && this.genres.contains(genre)) {
            this.genres.remove(genre); // Remove from Album's genres collection
            // Remove Album from Genre's albums collection (inverse side)
            if (genre.getAlbums() != null) { // Safety check for inverse side collection
                 genre.getAlbums().remove(this);
            }
        }
        return this;
    }

    // You can add bulk add/remove methods here as well
}