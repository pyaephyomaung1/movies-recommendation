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
public class GenreDTO {

    private int id;
    private String name;

    // Just IDs to avoid circular references
    private Set<Integer> movieIds;
    private Set<Integer> seriesIds;
}
