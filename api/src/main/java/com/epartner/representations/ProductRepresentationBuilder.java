package com.epartner.representations;

import com.epartner.domain.ProductImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ProductRepresentationBuilder {

    //TODO: Mover esto a otro lugar para poder usar spring cloud config
    private static final String BASE_IMAGE_URL = "http://localhost:18120/";
    private ProductRepresentation productRepresentation;

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Map<String, String> technicaSpeficication;
    private CategoryRepresentation categoryRepresentation;
    private List<ProductImage> images;

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

    public ProductRepresentationBuilder setImages(List<ProductImage> images) {
        this.images = images;
        return this;
    }

    public ProductRepresentationBuilder setProductRepresentation(ProductRepresentation productRepresentation){

        this.productRepresentation = productRepresentation;

        return this;
    }

    public ProductRepresentationBuilder setCategoryRepresentation(CategoryRepresentation categoryRepresentation){

        this.categoryRepresentation = categoryRepresentation;

        return this;
    }


    public ProductRepresentation createProductRepresentation() {
        List<ProductImageRepresentation> imagesRepresentation = Optional
                .ofNullable(images)
                .orElse(new ArrayList<>())
                .stream()
                .map(this::buildImageRepresentation)
                .collect(Collectors.toList());

        return new ProductRepresentation(id, name, description, stock, technicaSpeficication, categoryRepresentation, imagesRepresentation);
    }

    private ProductImageRepresentation buildImageRepresentation(ProductImage image){
        ProductImageRepresentation result = new ProductImageRepresentation();
        result.setId(image.getId());
        result.setPrincipal(image.getIsPrincipal());
        result.setUrl(BASE_IMAGE_URL + image.getFileName());
        return result;
    }
}