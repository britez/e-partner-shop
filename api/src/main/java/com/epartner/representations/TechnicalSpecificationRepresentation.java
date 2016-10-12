package com.epartner.representations;

/**
 * Created by mbritez on 24/09/16.
 */
public class TechnicalSpecificationRepresentation {

    private String value;
    private String key;
    private Long id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
