package com.epartner.domain.builders;

import com.epartner.domain.Tag;

import java.util.List;

public class TagBuilder {

    private Long id;
    private String name;
    private Boolean isCategory;
    private List products;

    public TagBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TagBuilder setIsCategory(Boolean isCategory) {
        this.isCategory = isCategory;
        return this;
    }

    public TagBuilder setProducts(List products) {
        this.products = products;
        return this;
    }

    public TagBuilder setId(Long id) {

        this.id = id;

        return this;
    }

    public Tag createTag() {

        Tag toCreate = new Tag();

        toCreate.setId(id);
        toCreate.setIsCategory(isCategory);
        toCreate.setName(name);
        toCreate.setProducts(products);

        return toCreate;

    }
}