package com.example.project.repository;

import com.example.project.models.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepo extends JpaRepository<Season, Integer> {}
