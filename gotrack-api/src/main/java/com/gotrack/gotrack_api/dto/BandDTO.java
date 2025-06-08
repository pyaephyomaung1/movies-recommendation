package com.gotrack.gotrack_api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandDTO {
    private int id;
    private String bandName;
    private String bandCover;
    private String activeYear;
    private String origin;
    private String overview;
    private Set<Integer> members;
    private Set<Integer> albumId;
    private Set<Integer> trackId;
}
