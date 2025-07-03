package com.example.project.repository;

import com.example.project.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepo extends JpaRepository<Actor, Integer> {}
