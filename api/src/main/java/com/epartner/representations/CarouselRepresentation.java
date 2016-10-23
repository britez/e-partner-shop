package com.epartner.representations;

import com.epartner.domain.ProductImage;

/**
 * Created by mapsi on 10/22/16.
 */
public class CarouselRepresentation {

    private Long id;
    private ProductImage backgroundImage;
    private ProductImage principalImage;
    private String title;
    private String titleUrl;
    private String subtitle;
    private String subtitleUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(ProductImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public ProductImage getPrincipalImage() {
        return principalImage;
    }

    public void setPrincipalImage(ProductImage principalImage) {
        this.principalImage = principalImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public void setSubtitleUrl(String subtitleUrl) {
        this.subtitleUrl = subtitleUrl;
    }
}
