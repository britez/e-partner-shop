package com.epartner.representations;

import java.util.Map;

/**
 * Created by maty on 1/9/16.
 */
public class ProductRepresentation {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Map<String, String> technicaSpeficication;
    private CategoryRepresentation category;

    private byte[] image;

    public ProductRepresentation(){}

    public ProductRepresentation(Long id, String name, String description, Integer stock, Map<String, String> technicaSpeficication, byte[] image, CategoryRepresentation categoryRepresentation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.technicaSpeficication = technicaSpeficication;
        this.image = image;
        this.category = categoryRepresentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Map<String, String> getTechnicaSpeficication() {
        return technicaSpeficication;
    }

    public void setTechnicaSpeficication(Map<String, String> technicaSpeficication) {
        this.technicaSpeficication = technicaSpeficication;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CategoryRepresentation getCategory() {
        return category;
    }

    public void setCategory(CategoryRepresentation category) {
        this.category = category;
    }
}
