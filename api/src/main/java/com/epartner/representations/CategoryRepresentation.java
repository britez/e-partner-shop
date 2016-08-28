package com.epartner.representations;

import com.epartner.domain.Category;

/**
 * Created by mbritez on 28/08/16.
 */
public class CategoryRepresentation {

    private String name;
    private String description;

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

    public Category convert() {
        Category result = new Category();
        result.setName(this.getName());
        result.setDescription(this.getDescription());
        return result;
    }


}
