package com.epartner.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

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
    @JoinTable(
            name="tag_products",
            joinColumns={@JoinColumn(name="tag_id_id")},
            inverseJoinColumns={@JoinColumn(name="product_id")})
    @Cascade(CascadeType.ALL)
    private List<Product> products;

    public Tag() {
        this.products = new ArrayList<>();
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
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
