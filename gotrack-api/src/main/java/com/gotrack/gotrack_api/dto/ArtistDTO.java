package com.gotrack.gotrack_api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {
    private int id;
    private String name;
    private String profileImage;
    private String dateOfBirth;
    private String dateOfDeath;
    private String nationality;
    private String biography;
    private Set<BandDTO> bands;
    private Set<AlbumDTO> albums;
    private Set<TrackDTO> tracks;
}
