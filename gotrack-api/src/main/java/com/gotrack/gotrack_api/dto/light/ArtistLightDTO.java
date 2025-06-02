package com.gotrack.gotrack_api.dto.light;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistLightDTO {
    private int id;
    private String name;
    private String profileImage;
}
