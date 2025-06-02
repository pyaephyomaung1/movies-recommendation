package com.gotrack.gotrack_api.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Album> albums;

    @ManyToMany(mappedBy= "genres")
    private Set<Track> tracks;

    @ManyToMany(mappedBy="genres")
    private Set<Artist> artists;

    @ManyToMany(mappedBy="genres")
    private Set<Band> bands;
}