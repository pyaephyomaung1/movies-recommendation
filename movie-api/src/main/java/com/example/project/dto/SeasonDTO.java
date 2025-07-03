package com.example.project.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDTO {

    private int id;
    private int seasonNumber;
    private LocalDate releasedDate;
    private String poster;
    private String description;

    // Just series ID to avoid circular reference
    private int seriesId;
    private String seriesTitle;

    // Nested episodes
    private Set<EpisodeDTO> episodes;
}
