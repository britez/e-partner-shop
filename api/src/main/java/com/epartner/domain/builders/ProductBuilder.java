package com.epartner.domain.builders;

import com.epartner.domain.Category;
import com.epartner.domain.Product;

import java.util.Map;

public class ProductBuilder {
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Map<String, String> technicaSpeficication;
    private byte[] image;
    private Category category;

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductBuilder setTechnicaSpeficication(Map<String, String> technicaSpeficication) {
        this.technicaSpeficication = technicaSpeficication;
        return this;
    }

    public ProductBuilder setCategory(Category category){
        this.category = category;
        return this;
    }

    public Product createProduct() {
        return new Product(id, name, description, stock);
    }
}