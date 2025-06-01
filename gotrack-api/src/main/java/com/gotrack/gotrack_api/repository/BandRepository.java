package com.gotrack.gotrack_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrack.gotrack_api.model.Band;

public interface BandRepository extends JpaRepository<Band, Integer>{
    
}
