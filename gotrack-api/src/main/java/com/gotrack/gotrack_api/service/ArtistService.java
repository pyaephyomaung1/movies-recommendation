package com.gotrack.gotrack_api.service;

import org.springframework.stereotype.Service;

import com.gotrack.gotrack_api.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    
    
}
