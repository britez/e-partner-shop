package com.epartner.converters;

import com.epartner.domain.Carousel;
import com.epartner.representations.CarouselRepresentation;
import com.epartner.representations.CategoryRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mapsi on 10/22/16.
 */
@Component
public class CarouselConverter {

    public Page<CarouselRepresentation> convert(Page<Carousel> stored) {
        PageImpl<CarouselRepresentation> result;
        List<CarouselRepresentation> content = stored
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        result = new PageImpl<>(content);
        return result;
    }

    public CarouselRepresentation convert(Carousel carousel) {
        CarouselRepresentation result = new CarouselRepresentation();
        result.setId(carousel.getId());
        result.setTitle(carousel.getTitle());
        result.setTitleUrl(carousel.getTitleUrl());
        result.setSubtitle(carousel.getSubtitle());
        result.setSubtitleUrl(carousel.getSubtitleUrl());
        return result;
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
