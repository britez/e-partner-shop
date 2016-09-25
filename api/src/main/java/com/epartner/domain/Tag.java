package com.epartner.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 24/09/16.
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean isCategory;

    @ManyToMany
    @JoinTable(name="tag_products"
            ,joinColumns={@JoinColumn(name="tag_id_id")}
            ,inverseJoinColumns={@JoinColumn(name="product_id")})
    private List<Product> products;

    public void addProduct(Product product) {

        if(this.products == null) {

            this.products = new ArrayList<>();
        }

        this.products.add(product);
       // product.addTag(this);
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

    public Boolean getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(Boolean category) {
        isCategory = category;
    }

    public List<Product> getProducts() {

        if(products == null){
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }
}
