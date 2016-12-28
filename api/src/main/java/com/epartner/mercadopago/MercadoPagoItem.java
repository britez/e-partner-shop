package com.epartner.mercadopago;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by maty on 9/12/16.
 */
public class MercadoPagoItem {

    private static final String CURRENCY = "ARS";

    /*
    "id": "item-ID-1234",
			"title": "Title of what you are paying for. It will be displayed in the payment process.",
			"currency_id": "ARS",
			"picture_url": "https://www.mercadopago.com/org-img/MP3/home/logomp3.gif",
			"description": "Item description",
			"category_id": "art", // Available categories at https://api.mercadopago.com/item_categories
			"quantity": 1,
			"unit_price": 100
     */

    private String id;
    private String title;
    @JsonProperty("currency_id")
    private String currencyId = CURRENCY;
    @JsonProperty("picture_url")
    private String pictureUrl;
    private String description;
    private Integer quantity;
    @JsonProperty("unit_price")
    private Double unitPrice;

    public static String getCURRENCY() {
        return CURRENCY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
