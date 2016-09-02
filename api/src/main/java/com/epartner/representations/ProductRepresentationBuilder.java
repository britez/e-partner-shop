package com.epartner.representations;

import java.util.Map;


public class ProductRepresentationBuilder {

    private ProductRepresentation productRepresentation;
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Map<String, String> technicaSpeficication;
    private byte[] image;
    private CategoryRepresentation categoryRepresentation;

    public ProductRepresentationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductRepresentationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductRepresentationBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductRepresentationBuilder setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductRepresentationBuilder setTechnicaSpeficication(Map<String, String> technicaSpeficication) {
        this.technicaSpeficication = technicaSpeficication;
        return this;
    }

    public ProductRepresentationBuilder setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public ProductRepresentationBuilder setProductRepresentation(ProductRepresentation productRepresentation){

        this.productRepresentation = productRepresentation;

        return this;
    }
    public ProductRepresentation createProductRepresentation() {
        return new ProductRepresentation(id, name, description, stock, technicaSpeficication, image, categoryRepresentation);
    }
}