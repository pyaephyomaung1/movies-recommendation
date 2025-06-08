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

    
}