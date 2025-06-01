package com.gotrack.gotrack_api.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private int id;
    private String title;
    private String titleCover;
    private String releasedDate;
    private List<TrackDTO> tracks;
    private Set<GenreDTO> genres;
    private ArtistDTO artistOwner;
    private BandDTO bandOwner;
}
