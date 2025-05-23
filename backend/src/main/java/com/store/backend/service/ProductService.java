package com.store.backend.service;

import com.store.backend.api.ProductDTO;
import com.store.backend.models.Category;
import com.store.backend.models.Product;
import com.store.backend.repository.CategoryRepository;
import com.store.backend.repository.ProductRepository;
import com.store.backend.utils.EntityUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDTO create(ProductDTO productDTO) {
        // Validate and fetch category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Create new Product entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setInStock(productDTO.getInStock());
        product.setMainImageUrl(productDTO.getMainImageUrl());
        product.setDetailImageUrls(productDTO.getDetailImageUrls());
        product.setCategory(category);

        // Save and return DTO
        Product saved = productRepository.save(product);
        return EntityUtils.toProductDTO(saved);
    }

    public ProductDTO update(ProductDTO productDTO) {
        // Fetch existing product
        Product existingProduct = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Apply updates if present
        if (productDTO.getName() != null && !productDTO.getName().isBlank()) {
            existingProduct.setName(productDTO.getName());
        }
        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            existingProduct.setDescription(productDTO.getDescription());
        }
        if (productDTO.getInStock() != null) {
            existingProduct.setInStock(productDTO.getInStock());
        }
        if (productDTO.getMainImageUrl() != null && !productDTO.getMainImageUrl().isBlank()) {
            existingProduct.setMainImageUrl(productDTO.getMainImageUrl());
        }
        if (productDTO.getDetailImageUrls() != null && !productDTO.getDetailImageUrls().isEmpty()) {
            existingProduct.setDetailImageUrls(productDTO.getDetailImageUrls());
        }
        if (productDTO.getCategoryId() != null) {
            Category newCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            existingProduct.setCategory(newCategory);
        }

        // Save and return updated product
        Product saved = productRepository.save(existingProduct);
        return EntityUtils.toProductDTO(saved);
    }

    public ProductDTO findById(int id) {
        return productRepository.findById(id)
                .map(EntityUtils::toProductDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(EntityUtils::toProductDTO)
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }
}