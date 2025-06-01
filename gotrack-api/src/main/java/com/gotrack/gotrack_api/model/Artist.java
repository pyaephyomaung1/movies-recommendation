package com.gotrack.gotrack_api.model;

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
    private Set<Band> bands;

    // One-to-many: artist's solo albums
    @OneToMany(mappedBy = "artistOwner")
    private Set<Album> albums;

    // One-to-many: artist's solo tracks
    @OneToMany(mappedBy = "artist")
    private Set<Track> tracks;
}