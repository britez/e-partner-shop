package com.epartner.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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

    /*@ElementCollection
    @JoinTable(name="technical_specification", joinColumns=@JoinColumn(name="product_id"))
    @MapKeyColumn (name="technical_specification_id")
    @Column(name="technica_speficication")
    private Map<String, String> technicaSpeficication = new HashMap<>();*/

    public Product(Long id, String name,
                   String description,
                   Integer stock, Map<String, String> technicaSpeficication,
                   byte[] image, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        //this.technicaSpeficication = technicaSpeficication;
        this.image = image;
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name="catgory_id")
    private Category category;

    private byte[] image;


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

    /*public Map<String, String> getTechnicaSpeficication() {
        return technicaSpeficication;
    }

    public void setTechnicaSpeficication(Map<String, String> technicaSpeficication) {
        this.technicaSpeficication = technicaSpeficication;
    }*/

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(){}


}
