package com.store.backend.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column(nullable = false)
    private String name;

    @Column( nullable = false )
    private BigDecimal price;

    @Column( columnDefinition = "TEXT" )
    private String description;

    @Column( nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE" )
    private Boolean inStock = true;

    @Column( nullable = false )
    private String mainImageUrl;

    @ElementCollection
    @CollectionTable(
        name = "product_detail_images",
        joinColumns = @JoinColumn( name = "product_id" )
    )
    @Column(name = "image_url" , nullable = true)
    private List<String> detailImageUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn( name = "category_id" , nullable = false )
    private Category category;
}
