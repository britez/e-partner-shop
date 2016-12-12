package com.epartner.representations;

/**
 * Created by maty on 8/12/16.
 */
public class MercadoPagoPaymentRepresentation {

    String paymentUrl;


    public MercadoPagoPaymentRepresentation(){}

    public MercadoPagoPaymentRepresentation(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }



    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
