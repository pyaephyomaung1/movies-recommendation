package com.store.backend.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.store.backend.api.CategoryDTO;
import com.store.backend.api.ProductDTO;
import com.store.backend.models.Category;
import com.store.backend.models.Product;

public class EntityUtils {
    public static CategoryDTO toCategoryDTO(Category category) {
        List<ProductDTO> products = category.getProducts().stream().map(product -> new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getInStock(),
                product.getMainImageUrl(),
                product.getDetailImageUrls(),
                product.getCategory().getId())).collect(Collectors.toList());
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                products);
    }

    public static Category toCategory(CategoryDTO categoryDTO, List<Product> products) {
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                products);
    }

    public static ProductDTO toProductDTO(Product product) {
        String basedUrl = "http://localhost:8080/images/";
        String mainImageUrl = basedUrl + product.getMainImageUrl();
        List<String> detailImages = product.getDetailImageUrls()
                .stream()
                .map(imageUrl -> basedUrl + imageUrl)
                .collect(Collectors.toList());
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getDescription(),
            product.getInStock(),
            mainImageUrl,
            detailImages,
            product.getCategory().getId()
        );
    }

    public static Product toProduct(ProductDTO productDTO, Category category){
        return new Product(
            productDTO.getId(),
            productDTO.getName(),
            productDTO.getPrice(),
            productDTO.getDescription(),
            productDTO.getInStock(),
            productDTO.getMainImageUrl(),
            productDTO.getDetailImageUrls(),
            category
        ); 
    }
}