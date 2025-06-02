package com.gotrack.gotrack_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity; // Consider replacing
import jakarta.persistence.GeneratedValue; // Added
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Added
import jakarta.persistence.ManyToMany;
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
@Table(name = "genre")
// Exclude relationship fields from equals and hashCode to prevent infinite recursion
@EqualsAndHashCode(exclude = {"albums", "tracks", "artists", "bands"})
// Exclude relationship fields from toString to prevent infinite recursion
@ToString(exclude = {"albums", "tracks", "artists", "bands"})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // Inverse side of Many-to-Many relationships - uses 'mappedBy'
    // These collections are managed by the owning side (Album, Track, Artist, Band)

    @ManyToMany(mappedBy = "genres")
    private Set<Album> albums = new HashSet<>(); // Initialize

    @ManyToMany(mappedBy = "genres")
    private Set<Track> tracks = new HashSet<>(); // Initialize

    @ManyToMany(mappedBy="genres")
    private Set<Artist> artists = new HashSet<>(); // Initialize

     @ManyToMany(mappedBy="genres")
    private Set<Band> bands = new HashSet<>(); // Initialize

    // --- Relationship Management Methods (Primarily for Convenience/Inverse Side) ---
    // It's usually best to manage ManyToMany from the owning side (Album, Track, Artist, Band)
    // These methods just update the inverse side's collection and optionally the owning side.

    public Genre addAlbum(Album album) {
        if (album != null) {
            this.albums.add(album);
             if (album.getGenres() != null) { // Safety check for owning side collection
                 album.getGenres().add(this); // Update owning side (Album)
            }
        }
        return this;
    }

    public Genre removeAlbum(Album album) {
         if (album != null && this.albums.contains(album)) {
            this.albums.remove(album);
             if (album.getGenres() != null) { // Safety check for owning side collection
                 album.getGenres().remove(this); // Update owning side (Album)
            }
        }
        return this;
    }

    public Genre addTrack(Track track) {
        if (track != null) {
            this.tracks.add(track);
             if (track.getGenres() != null) { // Safety check for owning side collection
                 track.getGenres().add(this); // Update owning side (Track)
            }
        }
        return this;
    }

    public Genre removeTrack(Track track) {
         if (track != null && this.tracks.contains(track)) {
            this.tracks.remove(track);
             if (track.getGenres() != null) { // Safety check for owning side collection
                 track.getGenres().remove(this); // Update owning side (Track)
            }
        }
        return this;
    }

    public Genre addArtist(Artist artist) {
        if (artist != null) {
            this.artists.add(artist);
             if (artist.getGenres() != null) { // Safety check for owning side collection
                 artist.getGenres().add(this); // Update owning side (Artist)
            }
        }
        return this;
    }

    public Genre removeArtist(Artist artist) {
         if (artist != null && this.artists.contains(artist)) {
            this.artists.remove(artist);
             if (artist.getGenres() != null) { // Safety check for owning side collection
                 artist.getGenres().remove(this); // Update owning side (Artist)
            }
        }
        return this;
    }

    public Genre addBand(Band band) {
        if (band != null) {
            this.bands.add(band);
             if (band.getGenres() != null) { // Safety check for owning side collection
                 band.getGenres().add(this); // Update owning side (Band)
            }
        }
        return this;
    }

    public Genre removeBand(Band band) {
         if (band != null && this.bands.contains(band)) {
            this.bands.remove(band);
             if (band.getGenres() != null) { // Safety check for owning side collection
                 band.getGenres().remove(this); // Update owning side (Band)
            }
        }
        return this;
    }
}