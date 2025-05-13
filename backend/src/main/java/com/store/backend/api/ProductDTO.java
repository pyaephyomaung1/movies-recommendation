package com.store.backend.api;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Boolean inStock;
    private String mainImageUrl;
    private List<String> detailImageUrls;
    private long categoryId;
}
