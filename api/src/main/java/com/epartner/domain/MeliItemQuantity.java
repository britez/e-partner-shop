package com.epartner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mapsi on 11/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliItemQuantity {

    private Integer available_quantity;

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

}
