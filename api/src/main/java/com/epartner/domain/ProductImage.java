package com.epartner.domain;

import javax.persistence.*;

/**
 * Created by maty on 4/9/16.
 */
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String url;

    public ProductImage(){}

    public ProductImage(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
