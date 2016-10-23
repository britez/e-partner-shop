package com.epartner.services;

import com.epartner.converters.CarouselConverter;
import com.epartner.domain.Carousel;
import com.epartner.repositories.CarouselRepository;
import com.epartner.representations.CarouselRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

/**
 * Created by mapsi on 10/22/16.
 */
@Service
public class CarouselService {

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    private final CarouselRepository carouselRepository;
    private final CarouselConverter converter;

    @Autowired
    public CarouselService(
            CarouselRepository carouselRepository,
            CarouselConverter converter) {
        this.carouselRepository = carouselRepository;
        this.converter = converter;
    }

    public Page<CarouselRepresentation> getAll(Optional<Integer> max, Optional<Integer> page) {
        Page<Carousel> stored = carouselRepository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );
        return this.converter.convert(stored);

    }

    public CarouselRepresentation show(Long id) {
        Carousel carousel = this.get(id);
        return this.converter.convert(carousel);
    }

    private Carousel get(Long id) {
        return Optional.ofNullable(this.carouselRepository.findOne(id)).orElseThrow(EntityExistsException::new);
    }
}
