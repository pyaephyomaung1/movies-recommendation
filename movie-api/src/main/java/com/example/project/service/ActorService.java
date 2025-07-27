package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.ActorDTO;
import com.example.project.models.Actor;
import com.example.project.repository.ActorRepo;
import com.example.project.utils.EntityDTOUtils;

@Service
public class ActorService {
    @Autowired
    private ActorRepo actorRepo;

    public List<ActorDTO> getActors(){
        List<Actor> actors = actorRepo.findAll();
        return actors.stream().map(EntityDTOUtils::toActorDTO).toList();
    }

    public ActorDTO getActorByID(int id){
        Actor actor = actorRepo.findById(id).orElseThrow(()-> new RuntimeException("Movie not found: " + id));
        return EntityDTOUtils.toActorDTO(actor);
    }
    
    public List<ActorDTO> searchByNameContaining(String name) {
        List<Actor> actors = actorRepo.searchByNameContaining(name);
        return actors.stream().map(EntityDTOUtils::toActorDTO).toList();
    }

    public List<ActorDTO> searchByNameStartingWith(String prefix) {
        List<Actor> actors = actorRepo.searchByNameStartingWith(prefix);
        return actors.stream().map(EntityDTOUtils::toActorDTO).toList();
    }

    public List<ActorDTO> searchByNamesIn(List<String> names) {
        List<Actor> actors = actorRepo.searchByNamesIn(names);
        return actors.stream().map(EntityDTOUtils::toActorDTO).toList();
    }
}
