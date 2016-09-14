package com.epartner.representations;

import java.util.ArrayList;
import java.util.List;
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
    private List<ProductImageRepresentation> productImageRepresentations;

    public ProductRepresentation(){
        this.productImageRepresentations = new ArrayList<>();
    }

    public ProductRepresentation(Long id, String name, String description, Integer stock, Map<String, String> technicaSpeficication, CategoryRepresentation categoryRepresentation, List<ProductImageRepresentation> productImageRepresentations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.technicaSpeficication = technicaSpeficication;
        this.category = categoryRepresentation;
        this.productImageRepresentations = productImageRepresentations;
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

    public CategoryRepresentation getCategory() {
        return category;
    }

    public void setCategory(CategoryRepresentation category) {
        this.category = category;
    }

    public void addProductImage(ProductImageRepresentation productImageRepresentation){

        this.productImageRepresentations.add(productImageRepresentation);
    }

    public List<ProductImageRepresentation> getProductImageRepresentations() {
        return productImageRepresentations;
    }

    public void setProductImageRepresentations(List<ProductImageRepresentation> productImageRepresentations) {
        this.productImageRepresentations = productImageRepresentations;
    }
}
