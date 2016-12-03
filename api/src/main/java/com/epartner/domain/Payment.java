package com.epartner.domain;

import javax.persistence.*;

/**
 * Created by mapsi on 11/26/16.
 */
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String paymenType;

    private PaymentState state = PaymentState.NOT_PAID;

    @ManyToOne
    private Product product;

    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymenType() {
        return paymenType;
    }

    public void setPaymenType(String paymenType) {
        this.paymenType = paymenType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }
}


