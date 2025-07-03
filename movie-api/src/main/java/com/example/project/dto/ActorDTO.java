package com.example.project.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorDTO {

    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String biography;
    private String profileImage;
    private boolean isAlive;
    private int age;

    private Set<Integer> movieIds;
    private Set<Integer> seriesIds;
}
