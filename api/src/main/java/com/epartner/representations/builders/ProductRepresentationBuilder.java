package com.epartner.representations.builders;

import com.epartner.domain.ProductImage;
import com.epartner.representations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepresentationBuilder {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private CategoryRepresentation category;
    private ProductImage principalImage;
    private List<ProductImage> images;
    private List<TechnicalSpecificationRepresentation> technicalSpecifications;
    private List<TagRepresentation> tags;
    private Boolean isImported;
    private Boolean isPublished;

    public ProductRepresentationBuilder setTechnicalSpecifications(List<TechnicalSpecificationRepresentation> technicalSpecificationRepresentations){
        this.technicalSpecifications = technicalSpecificationRepresentations;
        return this;
    }

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

    public ProductRepresentationBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductRepresentationBuilder setImages(List<ProductImage> images) {
        this.images = images;
        return this;
    }

    public ProductRepresentationBuilder setCategory(CategoryRepresentation category){
        this.category = category;
        return this;
    }

    public ProductRepresentationBuilder setTags(List<TagRepresentation> tags){
        this.tags = tags;
        return this;
    }

    public ProductRepresentation createProductRepresentation(String baseImageUrl) {
        List<ProductImageRepresentation> imagesRepresentation = Optional
                .ofNullable(images)
                .orElse(new ArrayList<>())
                .stream()
                .map(it -> this.buildImageRepresentation(it, baseImageUrl))
                .collect(Collectors.toList());

        ProductImageRepresentation imageRepresentation = Optional.ofNullable(this.principalImage)
                .map(it -> buildImageRepresentation(it, baseImageUrl))
                .orElse(null);

        ProductRepresentation result = new ProductRepresentation(id,
                name,
                description,
                stock,
                category,
                imageRepresentation,
                imagesRepresentation,
                price,
                technicalSpecifications,
                tags == null ? new ArrayList<>() : tags);

        result.setPublished(this.isPublished);
        result.setImported(this.isImported);
        return result;
    }

    private ProductImageRepresentation buildImageRepresentation(ProductImage image, String baseImageUrl){
        ProductImageRepresentation result = new ProductImageRepresentation();
        result.setId(image.getId());
        result.setUrl(Optional.ofNullable(image.getUrl()).orElse(baseImageUrl + image.getFileName()));
        return result;
    }

    public ProductRepresentationBuilder setPrincipalImage(ProductImage principalImage) {
        this.principalImage = principalImage;
        return this;
    }

    public ProductRepresentationBuilder setImported(Boolean imported) {
        isImported = imported;
        return this;
    }

    public ProductRepresentationBuilder setPublished(Boolean published) {
        isPublished = published;
        return this;
    }
}