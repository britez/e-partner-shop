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
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;
    private boolean isPrincipal;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public ProductImage(){}

    public ProductImage(String fileName, Product product) {
        this.fileName = fileName;
        this.product = product;
    }

    public void setIsPrincipal(boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }
}
