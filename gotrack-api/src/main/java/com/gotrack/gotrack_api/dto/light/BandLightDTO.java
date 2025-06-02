package com.gotrack.gotrack_api.dto.light;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandLightDTO {
    private int id;
    private String bandName;
    private String bandCover;
}
