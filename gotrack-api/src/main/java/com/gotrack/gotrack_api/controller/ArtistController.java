package com.gotrack.gotrack_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotrack.gotrack_api.dto.ArtistDTO;
import com.gotrack.gotrack_api.service.ArtistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/")
    public ResponseEntity<List<ArtistDTO>> getAll(){
        return ResponseEntity.ok(artistService.getArtists());
    }
}
