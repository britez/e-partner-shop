package com.epartner.representations;

import com.epartner.domain.PaymentState;

/**
 * Created by mapsi on 11/26/16.
 */
public class PaymentRepresentation {

    private Long id;
    private String paymentType;
    private SimpleProductRepresentation product;
    private Integer quantity;
    private PaymentState state;
    private String user;
    private Double price;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SimpleProductRepresentation getProduct() {
        return product;
    }

    public void setProduct(SimpleProductRepresentation product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
