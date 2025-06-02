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

    // --- Relationship Management Methods ---

    /**
     * Adds this artist to a band, updating both sides of the relationship.
     * @param band The band to join
     * @return this artist (for method chaining)
     */
    public Artist joinBand(Band band) {
        if (band != null) {
            this.bands.add(band);           // Add Band to Artist's bands collection (inverse side)
            band.getMembers().add(this);    // Add Artist to Band's members collection (owning side)
        }
        return this;
    }

    /**
     * Removes this artist from a band, updating both sides of the relationship.
     * @param band The band to leave
     * @return this artist (for method chaining)
     */
    public Artist leaveBand(Band band) {
        if (band != null) {
            this.bands.remove(band);        // Remove Band from Artist's bands collection
            band.getMembers().remove(this); // Remove Artist from Band's members collection
        }
        return this;
    }

    /**
     * Adds an album to this artist's collection (as a convenience method).
     * This delegates to Album.setArtistOwner() for the actual relationship management.
     *
     * @param album The album to add
     * @return this artist (for method chaining)
     */
    public Artist addAlbum(Album album) {
        if (album != null) {
            // Use the Album's method (owning side) to manage the relationship
            album.setArtistOwner(this);
            // Also add to our collection for consistency (redundant if Album's setter is perfect)
            // Check if already present to avoid duplicates if Set behaves unexpectedly
            if (!this.albums.contains(album)) {
                 this.albums.add(album);
            }
        }
        return this;
    }

    /**
     * Removes an album from this artist's collection.
     * This delegates to Album.setArtistOwner(null) for proper relationship management.
     *
     * @param album The album to remove
     * @return this artist (for method chaining)
     */
    public Artist removeAlbum(Album album) {
        // Check if album is not null AND is currently in our collection
        if (album != null && this.albums.contains(album)) {
            // Check if this artist is the actual owner before clearing the reference
            if (album.getArtistOwner() == this) {
                // Use the Album's method (owning side) to clear the relationship
                album.setArtistOwner(null);
            }
            // Also remove from our collection
            this.albums.remove(album);
        }
        return this;
    }

    /**
     * Adds a track to this artist's collection (as a convenience method).
     * This delegates to Track.setArtist() for the actual relationship management.
     *
     * @param track The track to add
     * @return this artist (for method chaining)
     */
    public Artist addTrack(Track track) {
        if (track != null) {
            // Use the Track's method (owning side) to manage the relationship
            track.setArtist(this);
             // Also add to our collection (redundant if Track's setter is perfect)
             // Check if already present to avoid duplicates if Set behaves unexpectedly
            if (!this.tracks.contains(track)) {
                 this.tracks.add(track);
            }
        }
        return this;
    }

    /**
     * Removes a track from this artist's collection.
     * This delegates to Track.setArtist(null) for proper relationship management.
     *
     * @param track The track to remove
     * @return this artist (for method chaining)
     */
    public Artist removeTrack(Track track) {
        // Check if track is not null AND is currently in our collection
        if (track != null && this.tracks.contains(track)) {
             // Check if this artist is the actual owner before clearing the reference
            if (track.getArtist() == this) {
                // Use the Track's method (owning side) to clear the relationship
                track.setArtist(null);
            }
            // Also remove from our collection
            this.tracks.remove(track);
        }
        return this;
    }

     /**
     * Adds a genre to this artist and updates both sides of the relationship.
     * This artist owns the Many-to-Many relationship with Genre.
     * @param genre The genre to add
     * @return this artist (for method chaining)
     */
    public Artist addGenre(Genre genre) {
        if (genre != null) {
            this.genres.add(genre);         // Add Genre to Artist's genres collection (owning side)
            // Add Artist to Genre's artists collection (inverse side)
            if(genre.getArtists() != null) { // Safety check for inverse side collection
                 genre.getArtists().add(this);
            }
        }
        return this;
    }

    /**
     * Removes a genre from this artist and updates both sides of the relationship.
     * @param genre The genre to remove
     * @return this artist (for method chaining)
     */
    public Artist removeGenre(Genre genre) {
        // Check if genre is not null AND is currently in our collection
        if (genre != null && this.genres.contains(genre)) {
            this.genres.remove(genre);          // Remove Genre from Artist's genres collection
             // Remove Artist from Genre's artists collection (inverse side)
             if(genre.getArtists() != null) { // Safety check for inverse side collection
                genre.getArtists().remove(this);
            }
        }
        return this;
    }
    // You can add bulk add/remove methods here as well
}