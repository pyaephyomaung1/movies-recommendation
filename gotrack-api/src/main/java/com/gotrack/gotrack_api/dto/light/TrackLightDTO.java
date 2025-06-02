package com.gotrack.gotrack_api.dto.light;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackLightDTO {
    private int id;
    private String title;
    private AlbumLightDTO albumLightDTO;
}
