package com.epartner.domain;

import com.epartner.representations.CategoryRepresentation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by mbritez on 28/08/16.
 */
@Entity
public class Category implements Serializable {

    public Category() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String description;

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

    public CategoryRepresentation convert() {
        CategoryRepresentation result = new CategoryRepresentation();
        result.setDescription(this.getDescription());
        result.setName(this.getName());
        return result;
    }
}
