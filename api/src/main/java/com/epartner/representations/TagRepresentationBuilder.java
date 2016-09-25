package com.epartner.representations;

import com.epartner.domain.Product;

import java.util.List;

/**
 * Created by martin on 24/09/16.
 */
public class TagRepresentationBuilder {


    private Long id;
    private Boolean isCategory;
    private List<ProductRepresentation> product;
    private String name;


    public TagRepresentationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TagRepresentationBuilder setIsCategory(Boolean isCategory) {
        this.isCategory = isCategory;
        return this;
    }

    public TagRepresentationBuilder setProducts(List<ProductRepresentation> product){
        this.product = product;
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
        toCreate.setProductRepresentationList(product);
        return toCreate;
    }


}
