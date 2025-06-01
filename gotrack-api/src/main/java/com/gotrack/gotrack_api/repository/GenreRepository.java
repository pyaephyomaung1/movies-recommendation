package com.gotrack.gotrack_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrack.gotrack_api.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
    
}
