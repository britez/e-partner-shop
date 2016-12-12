package com.epartner.mercadopago;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by maty on 9/12/16.
 */
public class MercadoPagoButtonData {


    private List<MercadoPagoItem> items;

    @JsonProperty("notification_url")
    private String notificationUrl;
    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("back_urls")
    private MercadoPagoBackUrls backUrls;

    public List<MercadoPagoItem> getItems() {
        return items;
    }

    public void setItems(List<MercadoPagoItem> items) {
        this.items = items;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public MercadoPagoBackUrls getBackUrls() {
        return backUrls;
    }

    public void setBackUrls(MercadoPagoBackUrls backUrls) {
        this.backUrls = backUrls;
    }
}
