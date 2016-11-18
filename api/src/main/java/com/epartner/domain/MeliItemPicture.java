package com.epartner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mapsi on 11/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliItemPicture {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
