package com.store.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.backend.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
