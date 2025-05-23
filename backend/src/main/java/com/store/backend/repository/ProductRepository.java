package com.store.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.backend.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer >{
    
}
