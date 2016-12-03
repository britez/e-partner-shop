package com.epartner.representations;

import com.epartner.domain.PaymentState;

/**
 * Created by mapsi on 11/26/16.
 */
public class PaymentRepresentation {

    private Long id;
    private String paymentType;
    private Long productId;
        private Integer quantity;
    private PaymentState state;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
}
