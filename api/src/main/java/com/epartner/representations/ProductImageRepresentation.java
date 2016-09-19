package com.epartner.representations;

/**
 * Created by maty on 4/9/16.
 */
public class ProductImageRepresentation {

    private String url;
    private Long id;
    private Boolean principal;

    public ProductImageRepresentation() {
    }

    public ProductImageRepresentation(Long id, String url, Boolean isPrincipal) {
        this.url = url;
        this.id = id;
        this.principal = isPrincipal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
