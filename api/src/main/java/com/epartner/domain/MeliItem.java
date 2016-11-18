package com.epartner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mapsi on 11/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliItem {

    private String id;
    private String title;
    private Double price;
    private Integer available_quantity;
    private List<MeliItemPicture> pictures;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public List<MeliItemPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<MeliItemPicture> pictures) {
        this.pictures = pictures;
    }
}
