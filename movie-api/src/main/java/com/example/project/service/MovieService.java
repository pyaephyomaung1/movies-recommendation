package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.MovieDTO;
import com.example.project.repository.MovieRepo;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    
    
}
