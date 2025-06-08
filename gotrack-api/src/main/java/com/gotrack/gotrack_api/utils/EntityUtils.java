package com.gotrack.gotrack_api.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.dto.BandDTO;
import com.gotrack.gotrack_api.model.Album;
import com.gotrack.gotrack_api.model.Artist;
import com.gotrack.gotrack_api.model.Band;
import com.gotrack.gotrack_api.model.Genre;
import com.gotrack.gotrack_api.model.Track;



public class EntityUtils {
    public static String imagePath = "http://localhost:8080/images/";
    
    public static ArtistDTO toArtistDTO(Artist artist){
        Set<Integer> bandId = artist.getBands().stream().map(Band::getId).collect(Collectors.toSet());
        Set<Integer> albumId = artist.getAlbums().stream().map(Album::getId).collect(Collectors.toSet());
        Set<Integer> trackId = artist.getTracks().stream().map(Track::getId).collect(Collectors.toSet());
        Set<Integer> genreId = artist.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());

        String imageUrl = imagePath + artist.getProfileImage();
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setId(artist.getId());
        artistDTO.setName(artist.getName());
        artistDTO.setProfileImage(imageUrl);
        artistDTO.setDateOfBirth(artist.getDateOfBirth());
        artistDTO.setDateOfBirth(artist.getDateOfDeath());
        artistDTO.setNationality(artist.getNationality());
        artistDTO.setBiography(artist.getBiography());
        artistDTO.setBandId(bandId);
        artistDTO.setAlbumId(albumId);
        artistDTO.setTrackId(trackId);
        artistDTO.setGenreId(genreId);
        return artistDTO;
    }

    public static BandDTO toBandDTO(Band band){

        String imageUrl = imagePath + band.getBandCover();
        Set<Integer> memberId = band.getMembers().stream().map(Artist::getId).collect(Collectors.toSet());
        Set<Integer> albumId = band.getAlbums().stream().map(Album::getId).collect(Collectors.toSet());
        Set<Integer> trackId = band.getTracks().stream().map(Track::getId).collect(Collectors.toSet());

        BandDTO bandDTO = new BandDTO();
        bandDTO.setId(band.getId());
        bandDTO.setBandName(band.getBandName());
        bandDTO.setBandCover(imageUrl);
        bandDTO.setActiveYear(band.getActiveYear());
        bandDTO.setOverview(band.getOverview());
        bandDTO.setOverview(band.getOverview());
        bandDTO.set
        return bandDTO;
    }
}   

//  private int id;
//     private String bandName;
//     private String bandCover;
//     private String activeYear;
//     private String origin;
//     private String overview;
//     private Set<Integer> members;
//     private Set<Integer> albumId;
//     private Set<Integer> trackId;
