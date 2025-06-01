package com.gotrack.gotrack_api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
    private int id;
    private String title;
    private String audioFile;
    private AlbumDTO album;
    private ArtistDTO artist;
    private BandDTO band;
    private Set<GenreDTO> genres;
}
