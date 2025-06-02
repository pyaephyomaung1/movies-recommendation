package com.gotrack.gotrack_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToMany
    @JoinTable(
        name = "band_members",
        joinColumns = @JoinColumn(name = "band_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> members  = new HashSet<>();

    // One-to-many: albums released by the band
    @OneToMany(mappedBy = "bandOwner")
    private Set<Album> albums  = new HashSet<>();

    // One-to-many: tracks released by the band (non-album singles, if any)
    @OneToMany(mappedBy = "band")
    private Set<Track> tracks  = new HashSet<>();

    // Many-to-many: band genres (Band owns the relationship)
    @ManyToMany // Band owns the relationship as no 'mappedBy'
    @JoinTable(
        name = "band_genres", // Assuming a join table for Band-Genre
        joinColumns = @JoinColumn(name = "band_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

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
            this.albums.add(album);
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
     * Adds an artist as a member to this band, updating both sides.
     * This band owns the Many-to-Many relationship with Artist members.
     * @param member The artist to add as a member
     * @return this band (for method chaining)
     */
    public Band addMember(Artist member){
        if(member != null) {
            this.members.add(member);       // Add to Band's members collection
            member.getBands().add(this);    // Add Band to Artist's bands collection
        }
        return this;
    }

    /**
     * Removes an artist member from this band, updating both sides.
     * @param member The artist to remove
     * @return this band (for method chaining)
     */
     public Band removeMember(Artist member){
        // Check if member is not null AND is currently in our collection
        if(member != null && this.members.contains(member)){
            this.members.remove(member);        // Remove from Band's members collection
            member.getBands().remove(this);     // Remove Band from Artist's bands collection
        }
        return this;
    }

    /**
     * Adds a track released by this band. Delegates to Track.setBand().
     * @param track The track to add
     * @return this band (for method chaining)
     */
    public Band addTrack(Track track) {
        // Corrected return type from Artist to Band
        if (track != null) {
            // Delegate to the Track's setter which manages the foreign key
            track.setBand(this);
             // Add to our collection (redundant if track.setBand is implemented correctly, but safe)
             this.tracks.add(track);
        }
        return this; // Return Band
    }

    /**
     * Removes a track from this band's releases. Delegates to Track.setBand(null).
     * @param track The track to remove
     * @return this band (for method chaining)
     */
    public Band removeTrack(Track track) {
        // Corrected return type from Artist to Band
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
        return this; // Return Band
    }

     /**
     * Adds a genre associated with this band, updating both sides.
     * This band owns the Many-to-Many relationship with Genre.
     * @param genre The genre to add
     * @return this band (for method chaining)
     */
    public Band addGenre(Genre genre) {
        // Corrected return type from Artist to Band
        if (genre != null) {
            this.genres.add(genre);         // Add to Band's genres collection
            
            // Corrected: Add Band to Genre's bands collection
            if (genre.getBands() != null) { // Safety check
                 genre.getBands().add(this);
            }
        }
        return this; // Return Band
    }

    /**
     * Removes a genre associated with this band, updating both sides.
     * @param genre The genre to remove
     * @return this band (for method chaining)
     */
    public Band removeGenre(Genre genre) {
        // Corrected return type from Artist to Band
        // Check if genre is not null AND is currently in our collection
        if (genre != null && this.genres.contains(genre)) {
            this.genres.remove(genre);          // Remove from Band's genres collection
            
            // Remove Band from Genre's bands collection
            if (genre.getBands() != null) { // Safety check
                 genre.getBands().remove(this);
            }
        }
        return this; // Return Band
    }
}