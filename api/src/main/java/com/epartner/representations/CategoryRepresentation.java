package com.epartner.representations;

/**
 * Created by mbritez on 28/08/16.
 */
public class CategoryRepresentation {

    private Long id;
    private String name;
    private String description;
    private Boolean highlight;
    private Long totalProducts;

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

    public Boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }
}
