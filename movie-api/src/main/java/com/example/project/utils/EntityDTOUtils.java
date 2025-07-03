package com.example.project.utils;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.project.dto.ActorDTO;
import com.example.project.dto.EpisodeDTO;
import com.example.project.dto.GenreDTO;
import com.example.project.dto.MovieDTO;
import com.example.project.dto.SeasonDTO;
import com.example.project.dto.SeriesDTO;
import com.example.project.models.Actor;
import com.example.project.models.Episode;
import com.example.project.models.Genre;
import com.example.project.models.Movie;
import com.example.project.models.Season;
import com.example.project.models.Series;

@Component
public class EntityDTOUtils {
    public static ActorDTO toActorDTO(Actor actor){
        if (actor == null) return null;

        return ActorDTO.builder()
        .id(actor.getId())
        .name(actor.getName())
        .dateOfBirth(actor.getDateOfBirth())
        .dateOfDeath(actor.getDateOfDeath())
        .biography(actor.getBiography())
        .profileImage(actor.getProfileImage())
        .isAlive(actor.isAlive())
        .age(actor.getAge())
        .movieIds(actor.getMovies().stream().map(Movie::getId).collect(Collectors.toSet()))
        .seriesIds(actor.getSeries().stream().map(Series::getId).collect(Collectors.toSet()))
        .build();
    }

    public static Actor toActor(ActorDTO dto){
        if(dto == null) return null;
        Actor actor = new Actor();
        actor.setId(dto.getId());
        actor.setName(dto.getName());
        actor.setDateOfBirth(dto.getDateOfBirth());
        actor.setDateOfDeath(dto.getDateOfDeath());
        actor.setBiography(dto.getBiography());
        actor.setProfileImage(dto.getProfileImage());
        actor.setMovies(new HashSet<>());
        actor.setSeries(new HashSet<>());
        return actor;
    }

     public static GenreDTO toGenreDTO(Genre genre) {
        if (genre == null) return null;
        
        return GenreDTO.builder()
            .id(genre.getId())
            .name(genre.getName())
            .movieIds(genre.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toSet()))
            .seriesIds(genre.getSeries().stream()
                .map(Series::getId)
                .collect(Collectors.toSet()))
            .build();
    }
    
    public static Genre toGenreEntity(GenreDTO dto) {
        if (dto == null) return null;
        
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setMovies(new HashSet<>());
        genre.setSeries(new HashSet<>());
        
        return genre;
    }

     public static MovieDTO toMovieDTO(Movie movie) {
        if (movie == null) return null;
        
        return MovieDTO.builder()
            .id(movie.getId())
            .title(movie.getTitle())
            .releasedYear(movie.getReleasedYear())
            .description(movie.getDescription())
            .poster(movie.getPoster())
            .movieFile(movie.getMovieFile())
            .genres(movie.getGenres().stream()
                .map(EntityDTOUtils::toGenreDTO)
                .collect(Collectors.toSet()))
            .actors(movie.getActors().stream()
                .map(EntityDTOUtils::toActorDTO)
                .collect(Collectors.toSet()))
            .build();
    }
    
    public static Movie toMovieEntity(MovieDTO dto) {
        if (dto == null) return null;
        
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setReleasedYear(dto.getReleasedYear());
        movie.setDescription(dto.getDescription());
        movie.setPoster(dto.getPoster());
        movie.setMovieFile(dto.getMovieFile());
        movie.setGenres(new HashSet<>());
        movie.setActors(new HashSet<>());
        
        return movie;
    }

     public static SeriesDTO toSeriesDTO(Series series) {
        if (series == null) return null;
        
        return SeriesDTO.builder()
            .id(series.getId())
            .title(series.getTitle())
            .description(series.getDescription())
            .poster(series.getPoster())
            .genres(series.getGenres().stream()
                .map(EntityDTOUtils::toGenreDTO)
                .collect(Collectors.toSet()))
            .actors(series.getActors().stream()
                .map(EntityDTOUtils::toActorDTO)
                .collect(Collectors.toSet()))
            .seasons(series.getSeasons().stream()
                .map(EntityDTOUtils::toSeasonDTO)
                .collect(Collectors.toSet()))
            .build();
    }
    
    public static Series toSeriesEntity(SeriesDTO dto) {
        if (dto == null) return null;
        
        Series series = new Series();
        series.setId(dto.getId());
        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setPoster(dto.getPoster());
        series.setGenres(new HashSet<>());
        series.setActors(new HashSet<>());
        series.setSeasons(new HashSet<>());
        
        return series;
    }

    public static SeasonDTO toSeasonDTO(Season season) {
        if (season == null) return null;
        
        return SeasonDTO.builder()
            .id(season.getId())
            .seasonNumber(season.getSeasonNumber())
            .releasedDate(season.getReleasedDate())
            .poster(season.getPoster())
            .description(season.getDescription())
            .seriesId(season.getSeries().getId())
            .seriesTitle(season.getSeries().getTitle())
            .episodes(season.getEpisodes().stream()
                .map(EntityDTOUtils::toEpisodeDTO)
                .collect(Collectors.toSet()))
            .build();
    }
    
    public static Season toSeasonEntity(SeasonDTO dto) {
        if (dto == null) return null;
        
        Season season = new Season();
        season.setId(dto.getId());
        season.setSeasonNumber(dto.getSeasonNumber());
        season.setReleasedDate(dto.getReleasedDate());
        season.setPoster(dto.getPoster());
        season.setDescription(dto.getDescription());
        season.setEpisodes(new HashSet<>());
        
        return season;
    }  
    public static EpisodeDTO toEpisodeDTO(Episode episode) {
        if (episode == null) return null;
        
        return EpisodeDTO.builder()
            .id(episode.getId())
            .title(episode.getTitle())
            .episodeNumber(episode.getEpisodeNumber())
            .episodeFile(episode.getEpisodeFile())
            .seasonId(episode.getSeason().getId())
            .seasonNumber(episode.getSeason().getSeasonNumber())
            .seriesId(episode.getSeason().getSeries().getId())
            .seriesTitle(episode.getSeason().getSeries().getTitle())
            .build();
    }
    
    public static Episode toEpisodeEntity(EpisodeDTO dto) {
        if (dto == null) return null;
        
        Episode episode = new Episode();
        episode.setId(dto.getId());
        episode.setTitle(dto.getTitle());
        episode.setEpisodeNumber(dto.getEpisodeNumber());
        episode.setEpisodeFile(dto.getEpisodeFile());
        return episode;
    }

     public static Set<ActorDTO> toActorDTOSet(Set<Actor> actors) {
        return actors.stream()
            .map(EntityDTOUtils::toActorDTO)
            .collect(Collectors.toSet());
    }
    
    public static Set<GenreDTO> toGenreDTOSet(Set<Genre> genres) {
        return genres.stream()
            .map(EntityDTOUtils::toGenreDTO)
            .collect(Collectors.toSet());
    }
    
    public static Set<MovieDTO> toMovieDTOSet(Set<Movie> movies) {
        return movies.stream()
            .map(EntityDTOUtils::toMovieDTO)
            .collect(Collectors.toSet());
    }
    
    public static Set<SeriesDTO> toSeriesDTOSet(Set<Series> series) {
        return series.stream()
            .map(EntityDTOUtils::toSeriesDTO)
            .collect(Collectors.toSet());
    }
}
