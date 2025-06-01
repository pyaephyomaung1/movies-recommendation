package com.gotrack.gotrack_api.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title; 
    private String titleCover;
    private String releasedDate;

    // One-to-many: album tracks
    @OneToMany(mappedBy = "album")
    private List<Track> tracks;

    // Many-to-many: album genres
    @ManyToMany
    private Set<Genre> genres;

    // Many-to-one: Album can be owned by an artist OR a band, not both
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = true)
    private Artist artistOwner;

    @ManyToOne
    @JoinColumn(name = "band_id", nullable = true)
    private Band bandOwner;

    // You must ensure that only one is set at a time in your logic!
}
