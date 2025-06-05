package com.gotrack.gotrack_api.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.dto.light.AlbumLightDTO;
import com.gotrack.gotrack_api.dto.light.BandLightDTO;
import com.gotrack.gotrack_api.dto.light.TrackLightDTO;
import com.gotrack.gotrack_api.model.Artist;


public class EntityUtils {
    public static String imagePath = "http://localhost:8080/images/";
   
    public static ArtistDTO toArtistDTO(Artist artist) {
        String imageUrl = imagePath + artist.getProfileImage();
        Set<AlbumLightDTO> albums = artist.getAlbums().stream().map(album -> new AlbumLightDTO(
            album.getId(),
            album.getTitle(),
            album.getTitleCover(),
            album.getReleasedYear()
        )).collect(Collectors.toSet());
        Set<BandLightDTO> bands = artist.getBands().stream().map(band -> new BandLightDTO(
            band.getId(),
            band.getBandName(),
            band.getBandCover()
        )).collect(Collectors.toSet());
        Set<TrackLightDTO> tracks = artist.getTracks().stream().map(
            track -> {
                AlbumLightDTO albumDTO = new AlbumLightDTO(
                    track.getAlbum().getId(),
                    track.getAlbum().getTitle(),
                    track.getAlbum().getTitleCover(),
                    track.getAlbum().getReleasedYear()
                );
                return new TrackLightDTO(
                    track.getId(),
                    track.getTitle(),
                    albumDTO
                );
            }
        ).collect(Collectors.toSet());
        return new ArtistDTO(
            artist.getId(),
            artist.getName(),
            imageUrl,
            artist.getDateOfBirth(),
            artist.getDateOfDeath(),
            artist.getNationality(),
            artist.getBiography(),
            bands,
            albums,
            tracks
        );
    }

    // public static Artist toArtist(
    //     ArtistDTO dto,
    //     BandRepository bandRepository,
    //     AlbumRepository albumRepository,
    //     TrackRepository trackRepository,
    //     GenreRepository genreRepository
    // ){
    //     if(dto == null) return null;

    //     Artist artist = new Artist();

    //     return artist;
    // }
}
