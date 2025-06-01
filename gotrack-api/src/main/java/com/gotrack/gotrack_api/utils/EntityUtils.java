package com.gotrack.gotrack_api.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.gotrack.gotrack_api.dto.AlbumDTO;
import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.dto.BandDTO;
import com.gotrack.gotrack_api.dto.TrackDTO;
import com.gotrack.gotrack_api.model.Artist;


public class EntityUtils {
    public static String imagePath = "http://localhost:8080/images/";
   
    public static ArtistDTO toArtistDTO(Artist artist) {
        String imageUrl = imagePath + artist.getProfileImage();
        return new ArtistDTO(
            artist.getId(),
            artist.getName(),
            imageUrl,
            artist.getDateOfBirth(),
            artist.getDateOfDeath(),
            artist.getNationality(),
            artist.getBiography(),
            artist.getBands().stream().map(BandDTO::class)
            
        );
    }
}
