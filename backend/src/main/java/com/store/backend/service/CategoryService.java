package com.store.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.backend.api.CategoryDTO;
import com.store.backend.models.Category;
import com.store.backend.repository.CategoryRepository;
import com.store.backend.utils.EntityUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Create a new Category from DTO
     */
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName()); // Don’t set ID – let DB handle it
        Category saved = categoryRepository.save(category);
        return EntityUtils.toCategoryDTO(saved);
    }

    /**
     * Update existing Category
     */
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryDTO.getId()));

        existingCategory.setName(categoryDTO.getName());
        Category updated = categoryRepository.save(existingCategory);
        return EntityUtils.toCategoryDTO(updated);
    }

    /**
     * Get Category by ID
     */
    public CategoryDTO findById(long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
        return EntityUtils.toCategoryDTO(category);
    }

    /**
     * Get all Categories
     */
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(EntityUtils::toCategoryDTO)
            .collect(Collectors.toList());
    }

    /**
     * Delete Category by ID
     */
    public void delete(long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        categoryRepository.delete(category);
    }
}