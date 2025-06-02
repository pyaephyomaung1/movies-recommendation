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

    // --- Relationship Management Methods ---

    /**
     * Adds an artist as a member to this band, updating both sides.
     * This band owns the Many-to-Many relationship with Artist members.
     * @param artist The artist to add as a member
     * @return this band (for method chaining)
     */
    public Band addMember(Artist artist){
        if(artist != null) {
            this.members.add(artist);       // Add to Band's members collection (owning side)
            artist.getBands().add(this);    // Add Band to Artist's bands collection (inverse side)
        }
        return this;
    }

    /**
     * Removes an artist member from this band, updating both sides.
     * @param artist The artist to remove
     * @return this band (for method chaining)
     */
     public Band removeMember(Artist artist){
        // Check if member is not null AND is currently in our collection
        if(artist != null && this.members.contains(artist)){
            this.members.remove(artist);        // Remove from Band's members collection
            artist.getBands().remove(this);     // Remove Band from Artist's bands collection
        }
        return this;
    }

    /**
     * Adds an album to this band's discography.
     * This band becomes the owner of the album. Delegates to Album.setBandOwner().
     * @param album The album to add
     * @return this band (for method chaining)
     */
    public Band addAlbum(Album album){
        if(album != null) {
            // Delegate to the Album's setter which manages the foreign key
            album.setBandOwner(this);
            // Add to our collection (redundant if album.setBandOwner is implemented correctly, but safe)
            // Check if already present to avoid duplicates if Set behaves unexpectedly
             if (!this.albums.contains(album)) {
                 this.albums.add(album);
             }
        }
        return this;
    }

    /**
     * Removes an album from this band's discography.
     * Delegates to Album.setBandOwner(null).
     * @param album The album to remove
     * @return this band (for method chaining)
     */
    public Band removeAlbum(Album album){
        // Check if album is not null AND is currently in our collection
        if(album != null && this.albums.contains(album)){
            // Check if this band is the actual owner before nulling the foreign key
            if (album.getBandOwner() == this) {
                 // Delegate to the Album's setter to clear the foreign key
                album.setBandOwner(null);
            }
            // Remove from our collection
            this.albums.remove(album);
        }
        return this;
    }

    /**
     * Adds a track released by this band. Delegates to Track.setBand().
     * @param track The track to add
     * @return this band (for method chaining)
     */
    public Band addTrack(Track track) {
        if (track != null) {
            // Delegate to the Track's setter which manages the foreign key
            track.setBand(this);
             // Add to our collection (redundant if track.setBand is implemented correctly, but safe)
             // Check if already present to avoid duplicates if Set behaves unexpectedly
            if (!this.tracks.contains(track)) {
                 this.tracks.add(track);
            }
        }
        return this;
    }

    /**
     * Removes a track from this band's releases. Delegates to Track.setBand(null).
     * @param track The track to remove
     * @return this band (for method chaining)
     */
    public Band removeTrack(Track track) {
        // Check if track is not null AND is currently in our collection
        if (track != null && this.tracks.contains(track)){
             // Check if this band is the actual owner before nulling the foreign key
            if(track.getBand() == this){
                // Delegate to the Track's setter to clear the foreign key
                track.setBand(null);
            }
            // Remove from our collection
            this.tracks.remove(track);
        }
        return this;
    }

     /**
     * Adds a genre associated with this band, updating both sides.
     * This band owns the Many-to-Many relationship with Genre.
     * @param genre The genre to add
     * @return this band (for method chaining)
     */
    public Band addGenre(Genre genre) {
        if (genre != null) {
            this.genres.add(genre);         // Add to Band's genres collection (owning side)
            // Add Band to Genre's bands collection (inverse side)
            if (genre.getBands() != null) { // Safety check for inverse side collection
                 genre.getBands().add(this);
            }
        }
        return this;
    }

    /**
     * Removes a genre associated with this band, updating both sides.
     * @param genre The genre to remove
     * @return this band (for method chaining)
     */
    public Band removeGenre(Genre genre) {
        // Check if genre is not null AND is currently in our collection
        if (genre != null && this.genres.contains(genre)) {
            this.genres.remove(genre);          // Remove from Band's genres collection
             // Remove Band from Genre's bands collection (inverse side)
             if (genre.getBands() != null) { // Safety check for inverse side collection
                 genre.getBands().remove(this);
            }
        }
        return this;
    }
    // You can add bulk add/remove methods here as well
}