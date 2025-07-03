package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDTO {

    private int id;
    private String title;
    private int episodeNumber;
    private String episodeFile;

    private int seasonId;
    private int seasonNumber;
    private int seriesId;
    private String seriesTitle;
}
