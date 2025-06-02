package com.gotrack.gotrack_api.dto;

import java.util.Set;

import com.gotrack.gotrack_api.dto.light.AlbumLightDTO;
import com.gotrack.gotrack_api.dto.light.BandLightDTO;
import com.gotrack.gotrack_api.dto.light.TrackLightDTO;

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
    private Set<BandLightDTO> bands;
    private Set<AlbumLightDTO> albums;
    private Set<TrackLightDTO> tracks;
}
