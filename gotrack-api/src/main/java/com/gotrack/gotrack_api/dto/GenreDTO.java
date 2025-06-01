package com.gotrack.gotrack_api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private int id;
    private String name;
    private Set<AlbumDTO> albums;
    private Set<TrackDTO> tracks;
}
