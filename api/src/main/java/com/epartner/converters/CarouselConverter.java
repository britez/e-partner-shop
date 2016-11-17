package com.epartner.converters;

import com.epartner.domain.Carousel;
import com.epartner.domain.ProductImage;
import com.epartner.representations.CarouselRepresentation;
import com.epartner.representations.CategoryRepresentation;
import com.epartner.representations.ProductImageRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mapsi on 10/22/16.
 */
@Component
public class CarouselConverter {

    private final String baseImageUrl;

    @Autowired
    public CarouselConverter(
            @Value("${epartner.images.url}") String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public Page<CarouselRepresentation> convert(Page<Carousel> stored, Pageable pageRequest) {
        List<CarouselRepresentation> content = stored
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageRequest, stored.getTotalElements());
    }

    public CarouselRepresentation convert(Carousel carousel) {
        CarouselRepresentation result = new CarouselRepresentation();
        result.setId(carousel.getId());
        result.setTitle(carousel.getTitle());
        result.setTitleUrl(carousel.getTitleUrl());
        result.setSubtitle(carousel.getSubtitle());
        result.setSubtitleUrl(carousel.getSubtitleUrl());

        result.setBackgroundImage(convertImage(carousel.getBackgroundImage()).orElse(null));
        result.setPrincipalImage(convertImage(carousel.getPrincipalImage()).orElse(null));

        return result;
    }

    private Optional<ProductImageRepresentation> convertImage(ProductImage carousel) {
        ProductImageRepresentation result = null;
        if(Optional.ofNullable(carousel).isPresent()) {
            result = new ProductImageRepresentation();
            result.setId(carousel.getId());
            result.setUrl(this.baseImageUrl.concat(carousel.getFileName()));
        }
        return Optional.ofNullable(result);
    }

    public Carousel convert(CarouselRepresentation representation) {
        Carousel result = new Carousel();
        if(Optional.ofNullable(representation.getId()).isPresent()){
            result.setId(representation.getId());
        }
        result.setTitle(representation.getTitle());
        result.setTitleUrl(representation.getTitleUrl());
        result.setSubtitle(representation.getSubtitle());
        result.setSubtitleUrl(representation.getSubtitleUrl());
        return result;
    }
}
