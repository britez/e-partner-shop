package com.epartner.representations;

import java.util.List;

/**
 * Created by martin on 24/09/16.
 */
public class TagRepresentation {

    private Long id;
    private String name;
    private Boolean isCategory;
    private List<ProductRepresentation> productRepresentationList;


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

    public List<ProductRepresentation> getProductRepresentationList() {
        return productRepresentationList;
    }

    public void setProductRepresentationList(List<ProductRepresentation> productRepresentationList) {
        this.productRepresentationList = productRepresentationList;
    }

    public Boolean getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(Boolean category) {
        isCategory = category;
    }
}

