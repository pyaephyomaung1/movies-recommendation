package com.store.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.backend.api.ProductDTO;
import com.store.backend.models.Category;
import com.store.backend.models.Product;
import com.store.backend.repository.CategoryRepository;
import com.store.backend.repository.ProductRepository;
import com.store.backend.utils.EntityUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO create(ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategoryId())
        .orElseThrow(()-> new IllegalArgumentException("Category not found"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setInStock(productDTO.getInStock());
        product.setMainImageUrl(productDTO.getMainImageUrl());
        product.setDetailImageUrls(productDTO.getDetailImageUrls());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return EntityUtils.toProductDTO(saved);
    }

    public ProductDTO update(ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategoryId())
        .orElseThrow(()-> new IllegalArgumentException("category not Found"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setInStock(productDTO.getInStock());
        if( productDTO.getMainImageUrl() != null && !productDTO.getMainImageUrl().isBlank() ){
            product.setMainImageUrl(productDTO.getMainImageUrl());
        }

        if ( productDTO.getDescription() != null && !productDTO.getDescription().isBlank() ){
            product.setDetailImageUrls(productDTO.getDetailImageUrls());
        }   
        product.setCategory(category); 
        Product saved = productRepository.save(product);
        return EntityUtils.toProductDTO(saved);
    }   

    public ProductDTO findById(long id){
        return productRepository.findById(id)
        .map(EntityUtils::toProductDTO)
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    
}



// private long id;
//     private String name;
//     private BigDecimal price;
//     private String description;
//     private Boolean inStock;
//     private String mainImageUrl;
//     private List<String> detailImageUrls;
//     private long categoryId;
