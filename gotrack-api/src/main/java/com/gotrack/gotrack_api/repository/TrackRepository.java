package com.gotrack.gotrack_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrack.gotrack_api.model.Track;

public interface TrackRepository extends JpaRepository<Track, Integer>{
    
}
