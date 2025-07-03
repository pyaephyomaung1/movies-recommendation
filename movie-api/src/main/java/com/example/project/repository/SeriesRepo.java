package com.example.project.repository;

import com.example.project.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepo extends JpaRepository<Series, Integer> {}
