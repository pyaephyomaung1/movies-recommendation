package com.example.project.repository;

import com.example.project.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepo extends JpaRepository<Episode, Integer> {}
