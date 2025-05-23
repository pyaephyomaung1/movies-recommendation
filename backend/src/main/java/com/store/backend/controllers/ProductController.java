package com.store.backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.store.backend.api.ProductDTO;
import com.store.backend.image.ImageStore;
import com.store.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageStore imageStore;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> create(
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam String description,
            @RequestParam Boolean inStock,
            @RequestParam Integer categoryId,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("detailImages") List<MultipartFile> detailImages) {
        try {
            String mainImageFileName = imageStore.store(mainImage);
            List<String> detailImageUrls = new ArrayList<>();
            for (MultipartFile file : detailImages) {
                detailImageUrls.add(imageStore.store(file));
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
            e.printStackTrace(); // Optional: helpful for debugging
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> update(
            @PathVariable int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) Integer categoryId,
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
                    detailImageUrls.add(imageStore.store(file));
                }
            }

            ProductDTO updatedProduct = new ProductDTO();
            updatedProduct.setId(id);
            updatedProduct.setName(name);
            updatedProduct.setPrice(price);
            updatedProduct.setDescription(description);
            updatedProduct.setInStock(inStock);
            updatedProduct.setCategoryId(categoryId);
            updatedProduct.setMainImageUrl(mainImageFileName);
            updatedProduct.setDetailImageUrls(detailImageUrls);

            ProductDTO savedProduct = productService.update(updatedProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace(); // Optional for debugging
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable int id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}