package com.gotrack.gotrack_api.model;

import java.util.Set;

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
    private String overview;

    // Many-to-many: band members
    @ManyToMany
    @JoinTable(
        name = "band_members",
        joinColumns = @JoinColumn(name = "band_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> members;

    // One-to-many: albums released by the band
    @OneToMany(mappedBy = "bandOwner")
    private Set<Album> albums;

    // One-to-many: tracks released by the band (non-album singles, if any)
    @OneToMany(mappedBy = "band")
    private Set<Track> tracks;
}