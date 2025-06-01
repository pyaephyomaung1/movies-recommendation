package com.gotrack.gotrack_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrack.gotrack_api.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
    
}
