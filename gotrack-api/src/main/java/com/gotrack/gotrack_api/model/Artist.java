package com.gotrack.gotrack_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String profileImage;
    private String dateOfBirth;
    private String dateOfDeath;
    private String nationality;

    @Column(columnDefinition="TEXT")
    private String biography;

    // Many-to-many: artist can join multiple bands, band has many artists
    @ManyToMany(mappedBy = "members")
    private Set<Band> bands = new HashSet<>();

    // One-to-many: artist's solo albums
    @OneToMany(mappedBy = "artistOwner")
    private Set<Album> albums = new HashSet<>();;

    // One-to-many: artist's solo tracks
    @OneToMany(mappedBy = "artist")
    private Set<Track> tracks = new HashSet<>();;

    @ManyToMany
    private Set<Genre> genres = new HashSet<>();;


    public Artist joinBand(Band band){
        if( band != null ) {
            this.bands.add(band);
            band.getMembers().add(this);
        }   
        return this;
    }

    public Artist removeBand(Band band){
        if( band != null ) {
            this.bands.remove(band);
            band.getMembers().remove(this);
        }
        return  this;
    }

    public Artist addAlbum(Album album){
        if ( album != null ) {
            album.setArtistOwner(this);
            this.albums.add(album);
        }
        return this;
    }

    public Artist removeAlbum(Album album){
        if (album!=null){
            album.setArtistOwner(null);
            this.albums.remove(album);
        }
        return this;
    }

    public Artist addTrack(Track track){
        if(track != null){
            track.setArtist(this);
            this.tracks.add(track);
        }   
        return this;
    }

    public Artist removeTrack(Track track){
        if (track != null){
            if(track.getArtist() == this){
                track.setArtist(null);
            }
            this.tracks.remove(track);
        }
        return this;
    }

    public Artist addGenre(Genre genre){
        if(genre != null){
            this.genres.add(genre);
            genre.getArtists().add(this);
        }
        return this;
    }

    public Artist removeGenre(Genre genre){
        if (genre != null) {
            this.genres.remove(genre);
            genre.getArtists().remove(this);
        }
        return this;
    }
}