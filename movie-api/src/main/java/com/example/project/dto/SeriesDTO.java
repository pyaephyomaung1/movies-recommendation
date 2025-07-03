package com.example.project.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO {

    private int id;
    private String title;
    private String description;
    private String poster;

    // Nested objects for detailed info
    private Set<GenreDTO> genres;
    private Set<ActorDTO> actors;
    private Set<SeasonDTO> seasons;
}
