package com.epartner.domain;

import groovyjarjarcommonscli.Option;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by maty on 1/9/16.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;

    @OneToMany(mappedBy = "product")
    @Cascade(CascadeType.ALL)
    private List<TechnicalSpecification> technicalSpecifications;

    @OneToMany(mappedBy = "product")
    @Cascade(CascadeType.PERSIST)
    private List<ProductImage> images;

    @ManyToOne
    @JoinColumn(name="catgory_id")
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<Tag> tags;

    public Product(){
        this.technicalSpecifications = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public Product(Long id, String name, String description, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
    }

    public void addTag(Tag tag){

        if(tags == null){

            this.tags = new ArrayList<>();
        }

        this.tags.add(tag);
    }

    public List<TechnicalSpecification> getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    public void setTechnicalSpecifications(
            List<TechnicalSpecification> technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addImage(ProductImage image) {
        images.add(image);
    }

    public void addTechnicalSpecifications(List<TechnicalSpecification> technicalSpecificationList) {
        technicalSpecificationList.forEach(this::addTechnicalSpecification);
        removeSpecs(technicalSpecificationList);
    }

    private void removeSpecs(List<TechnicalSpecification> technicalSpecificationList) {
        List<TechnicalSpecification> toRemove = this.technicalSpecifications
                .stream()
                .filter(spec ->
                    Optional.ofNullable(
                        technicalSpecificationList
                            .stream()
                            .filter(it -> Optional.ofNullable(spec.getId()).isPresent() &&
                                    !spec.getId().equals(it.getId()))
                            .findFirst()
                            .orElse(null)).isPresent())
                .collect(Collectors.toList());

        toRemove.forEach(it -> {
            this.technicalSpecifications.remove(it);
            it.setProduct(null);
        });
    }

    private TechnicalSpecification addTechnicalSpecification(TechnicalSpecification technicalSpecification) {

        if(technicalSpecification.isNew()){
            technicalSpecification.setProduct(this);
            this.technicalSpecifications.add(technicalSpecification);
            return technicalSpecification;
        }else{
            TechnicalSpecification persisted = this.technicalSpecifications
                .stream()
                .filter(ts -> ts.getId().equals(technicalSpecification.getId()))
                .findFirst()
                .get();
            persisted
                .merge(technicalSpecification);
            return persisted;
        }
    }

    public void removeTechinicalSpecification(TechnicalSpecification technicalSpecification){
        this.technicalSpecifications.remove(technicalSpecification);
    }

    public List<Tag> getTags() {

        if(tags == null){

            tags = new ArrayList<>();
        }

        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}