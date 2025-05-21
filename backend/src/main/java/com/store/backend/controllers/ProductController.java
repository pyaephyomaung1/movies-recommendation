package com.store.backend.controllers;

import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.store.backend.api.ProductDTO;
import com.store.backend.image.ImageController;
import com.store.backend.image.ImageStore;
import com.store.backend.repository.CategoryRepository;
import com.store.backend.repository.ProductRepository;
import com.store.backend.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageController imageController;

    @Autowired
    private ImageStore imageStore;

    @Autowired
    private ProductRepository productRepository;

    ProductController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> create(
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam String description,
            @RequestParam Boolean inStock,
            @RequestParam Long categoryId,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("detailImages") List<MultipartFile> detailImages) {
        try {
            String mainImageFileName = imageStore.store(mainImage);
            List<String> detailImageUrls = new ArrayList<>();
            for (MultipartFile file : detailImages) {
                String fileName = imageStore.store(file);
                detailImageUrls.add(fileName);
            }
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(name);
            productDTO.setPrice(price);
            productDTO.setDescription(description);
            productDTO.setInStock(inStock);
            productDTO.setCategoryId(categoryId);
            productDTO.setMainImageUrl(mainImageFileName);
            productDTO.setDetailImageUrls(detailImageUrls);
            ProductDTO saved = productService.create(productDTO);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<ProductDTO> update(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam String description,
            @RequestParam Boolean inStock,
            @RequestParam Long categoryId,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "detailImages", required = false) List<MultipartFile> detailImages) {
        try {
            ProductDTO existingProduct = productService.findById(id);
            String mainImageFileName = existingProduct.getMainImageUrl();
            if (mainImage != null && !mainImage.isEmpty()) {
                mainImageFileName = imageStore.store(mainImage);
            }
            List<String> detailImageUrls = existingProduct.getDetailImageUrls();
            if (detailImages != null && !detailImages.isEmpty()) {
                detailImageUrls = new ArrayList<>();
                for (MultipartFile file : detailImages) {
                    String fileName = imageStore.store(file);
                    detailImageUrls.add(fileName);
                }
            }

            ProductDTO updatedProduct = new ProductDTO();
            updatedProduct.setId(id);
            updatedProduct.setName(name);
            updatedProduct.setPrice(price);
            updatedProduct.setDescription(description);
            updatedProduct.setInStock(inStock);
            updatedProduct.setCategoryId(id);
            updatedProduct.setMainImageUrl(mainImageFileName);
            updatedProduct.setDetailImageUrls(detailImageUrls);

            ProductDTO savedProduct = productService.update(updatedProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
