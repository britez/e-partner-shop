package com.epartner.representations.builders;

import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.TagRepresentation;

import java.util.List;

/**
 * Created by martin on 24/09/16.
 */
public class TagRepresentationBuilder {

    private Long id;
    private Boolean isCategory;
    private List<ProductRepresentation> products;
    private String name;

    public TagRepresentationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TagRepresentationBuilder setIsCategory(Boolean isCategory) {
        this.isCategory = isCategory;
        return this;
    }

    public TagRepresentationBuilder setProducts(List<ProductRepresentation> products){
        this.products = products;
        return this;
    }

    public TagRepresentationBuilder setName(String name){
        this.name = name;
        return this;
    }

    public TagRepresentation createTag() {
        TagRepresentation toCreate = new TagRepresentation();
        toCreate.setId(id);
        toCreate.setName(name);
        toCreate.setIsCategory(isCategory);
        toCreate.setProducts(products);
        return toCreate;
    }
}
