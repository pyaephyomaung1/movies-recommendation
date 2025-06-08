package com.gotrack.gotrack_api.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gotrack.gotrack_api.dto.AlbumDTO;
import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.dto.BandDTO;
import com.gotrack.gotrack_api.dto.GenreDTO;
import com.gotrack.gotrack_api.dto.TrackDTO;
import com.gotrack.gotrack_api.model.Album;
import com.gotrack.gotrack_api.model.Artist;
import com.gotrack.gotrack_api.model.Band;
import com.gotrack.gotrack_api.model.Genre;
import com.gotrack.gotrack_api.model.Track;



public class EntityUtils {
    public static String imagePath = "http://localhost:8080/image/";
    
    public static ArtistDTO toArtistDTO(Artist artist){
        Set<Integer> bandId = artist.getBands().stream().map(Band::getId).collect(Collectors.toSet());
        Set<Integer> albumId = artist.getAlbums().stream().map(Album::getId).collect(Collectors.toSet());
        Set<Integer> trackId = artist.getTracks().stream().map(Track::getId).collect(Collectors.toSet());
        Set<Integer> genreId = artist.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());

        String imageUrl = imagePath + "artist/" +artist.getProfileImage();
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
        bandDTO.setMembers(memberId);
        bandDTO.setAlbumId(albumId);
        bandDTO.setTrackId(trackId);
        return bandDTO;
    }

    public static AlbumDTO toAlbumDTO(Album album){
        String imageUrl = imagePath + album.getTitleCover();
        List<Integer> trackId = album.getTracks().stream().map(Track::getId).collect(Collectors.toList());
        Set<Integer> genreId = album.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        Integer artistOwnerId = album.getArtistOwner().getId();
        Integer bandOwerId = album.getBandOwner().getId();

        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(album.getId());
        albumDTO.setTitle(album.getTitle());
        albumDTO.setTitleCover(imageUrl);
        albumDTO.setReleasedYear(album.getReleasedYear());
        albumDTO.setTrackId(trackId);
        albumDTO.setGenreId(genreId);
        albumDTO.setArtistOwnerId(artistOwnerId);
        albumDTO.setBandOwnerId(bandOwerId);
        return albumDTO;
    }

    public static TrackDTO toTrackDTO(Track track){
        String imageUrl = imagePath + track.getAlbum().getTitleCover();

        Integer albumId = track.getAlbum().getId();
        Integer bandId = track.getBand().getId();
        Set<Integer> genreId = track.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());

        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(track.getId());
        trackDTO.setAudioFile(track.getAudioFile());
        trackDTO.setAlbumId(albumId);
        trackDTO.setBandId(bandId);
        trackDTO.setGenreId(genreId);
        return trackDTO;
    }


    public static GenreDTO toGenreDTO(Genre genre){
        Set<Integer> albumId = genre.getAlbums().stream().map(Album::getId).collect(Collectors.toSet());
        Set<Integer> trackId = genre.getTracks().stream().map(Track::getId).collect(Collectors.toSet());

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        genreDTO.setAlbumId(albumId);
        genreDTO.setTrackId(trackId);
        return genreDTO;
    }   
}   