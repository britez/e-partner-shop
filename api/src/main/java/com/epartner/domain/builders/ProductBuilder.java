package com.epartner.domain.builders;

import com.epartner.domain.Category;
import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import com.epartner.domain.TechnicalSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductBuilder {
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private List<TechnicalSpecification> technicaSpeficication;
    private Category category;
    private List<Tag> tags;

   public ProductBuilder setTechnicalSpecification(List<TechnicalSpecification> technicalSpecification){
       this.technicaSpeficication = technicalSpecification;
       return this;
   }

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

    public ProductBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setStock(Integer stock) {
        this.stock = stock;
        return this;
    }


    public ProductBuilder setCategory(Category category){
        this.category = category;
        return this;
    }

    public ProductBuilder setTags(List<Tag> tags){

        this.tags = tags;

        return this;
    }

    public Product createProduct() {

        Product result = new Product();
        result.setId(id);
        result.setName(name);
        result.setDescription(description);
        result.setTechnicalSpecifications(technicaSpeficication);
        result.setPrice(price);
        result.setStock(stock);
        result.setCategory(category);
        result.setTags(tags == null ? new ArrayList<>() : tags);

        return result;
    }
}