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
    private Integer albumId;
    private Integer artistId;
    private Integer bandId;
    private Set<Integer> genreId;
}
