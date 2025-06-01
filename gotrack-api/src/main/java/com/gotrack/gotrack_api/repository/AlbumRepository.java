package com.gotrack.gotrack_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrack.gotrack_api.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer>{
    
}
