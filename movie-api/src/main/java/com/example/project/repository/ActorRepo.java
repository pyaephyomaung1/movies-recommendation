package com.example.project.repository;

import com.example.project.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepo extends JpaRepository<Actor, Integer> {

    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Actor> searchByNameContaining(@Param("name") String name);

    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Actor> searchByNameStartingWith(@Param("prefix") String prefix);

    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) IN :names")
    List<Actor> searchByNamesIn(@Param("names") List<String> names);
}
