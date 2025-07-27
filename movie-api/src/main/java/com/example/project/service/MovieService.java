package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.MovieDTO;
import com.example.project.models.Movie;
import com.example.project.repository.MovieRepo;
import com.example.project.utils.EntityDTOUtils;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    public List<MovieDTO> getMovies(){
        List<Movie> movies = movieRepo.findAll();
        return movies.stream().map(EntityDTOUtils::toMovieDTO).toList();
    }

   public MovieDTO getMovieByID(int id) {
        Movie movie = movieRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
        return EntityDTOUtils.toMovieDTO(movie);
    }
}
