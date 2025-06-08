package com.gotrack.gotrack_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.repository.ArtistRepository;
import com.gotrack.gotrack_api.utils.EntityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    
    public List<ArtistDTO> getArtists(){
        return artistRepository.findAll().stream().map(EntityUtils::toArtistDTO).collect(Collectors.toList());
    }

    public ArtistDTO findById(int id){
        return artistRepository.findById(id).map(EntityUtils::toArtistDTO).orElseThrow(()-> new RuntimeException("Artist Not Found"));
    }
}
